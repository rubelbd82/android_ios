//
//  NewsViewController.m
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "NewsViewController.h"
#import "NewsDetailsViewController.h"

@interface NewsViewController ()

@end

@implementation NewsViewController
@synthesize market;
NSMutableArray *news;

int newsViews = 0;

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    NewsDetailsViewController *destViewController = segue.destinationViewController;
    destViewController.newsTitle = [[news objectAtIndex:[[self.tableView indexPathForSelectedRow] row]] valueForKey:@"c"];
    
    destViewController.newsDetails = [[news objectAtIndex:[[self.tableView indexPathForSelectedRow] row]] valueForKey:@"n"];;
    destViewController.market =market;
}

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self getNewsList];
    
    self.interstitial = [self createAndLoadInterstitial];
}

-(void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.tabBarController.tabBar.hidden = YES;
}

- (GADInterstitial *)createAndLoadInterstitial {
    GADInterstitial *interstitial =
    [[GADInterstitial alloc] initWithAdUnitID:interstitialAdUnitID];
    interstitial.delegate = self;
    [interstitial loadRequest:[GADRequest request]];
    return interstitial;
}

- (void)displayAd {
    if (newsViews%interstitialAdFrequency == 0) {
        if (self.interstitial.isReady) {
            [self.interstitial presentFromRootViewController:self];
        } else {
            NSLog(@"Ad wasn't ready");
        }
    }
    newsViews++;
}

- (void) getNewsList {
    
    NSString *urlString;
    
    if ([market isEqualToString:@"DSE"]) {
        urlString = [NSString stringWithFormat:@"%@dse_todays_news.php", baseUrl];
    } else if ([market isEqualToString:@"CSE"])  {
        urlString = [NSString stringWithFormat:@"%@cse_todays_news.php", baseUrl];
    }
    
    NSLog(@"URL String %@", urlString);
    
    bar = [[UIDownloadBar alloc] initWithURL:[NSURL URLWithString:urlString]
                            progressBarFrame:CGRectMake(40, 25, 200, 20)
                                     timeout:15
                                    delegate:self];
    alert =[[UIAlertView alloc]init];
    lblForDisplay=[[UILabel alloc]initWithFrame:CGRectMake(90, 40, 200, 20)];
    lblForDisplay.backgroundColor=[UIColor clearColor];
    lblForDisplay.text=@"Downloading...";
    lblForDisplay.textColor=[UIColor whiteColor];
    [alert addSubview:lblForDisplay];
    [alert addSubview:bar];
    [alert show];
    
}

#pragma mark - Download Bar
- (void)downloadBar:(UIDownloadBar *)downloadBar didFinishWithData:(NSData *)fileData suggestedFilename:(NSString *)filename {
    //  NSLog(@"%@", filename);
    //
    news = [NSJSONSerialization JSONObjectWithData:fileData options:kNilOptions error:nil]; // This is JSON Data;

    [alert dismissWithClickedButtonIndex:0 animated:YES];
    
    if ([news count] < 1) {
        UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Message"
                                                            message:@"No result found"
                                                           delegate:self
                                                  cancelButtonTitle:@"OK"
                                                  otherButtonTitles:nil];
        [alertView show];
    } else {
        [self displayAd];
    }
    [self.tableView reloadData];
}

- (void)downloadBar:(UIDownloadBar *)downloadBar didFailWithError:(NSError *)error {
    [alert dismissWithClickedButtonIndex:0 animated:YES];
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Error"
                                                        message:@"No Internet Connection"
                                                       delegate:self
                                              cancelButtonTitle:@"OK"
                                              otherButtonTitles:nil];
    [alertView show];
    
    
    NSLog(@"Download Error: %@", error);
}

- (void)downloadBarUpdated:(UIDownloadBar *)downloadBar {
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [news count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    // Configure the cell...
    
    if (nil == cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
    }
    
    NSString *companyName = [[news objectAtIndex:indexPath.row] valueForKey:@"c"];
       
     [cell.textLabel setText:companyName];

    cell.detailTextLabel.text = [[news objectAtIndex:indexPath.row] valueForKey:@"n"];    
    return  cell;
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

// Interristrial Ad

- (void)interstitialDidDismissScreen:(GADInterstitial *)interstitial {
    self.interstitial = [self createAndLoadInterstitial];
}


@end
