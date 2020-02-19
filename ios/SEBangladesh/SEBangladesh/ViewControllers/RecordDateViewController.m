//
//  RecordDateViewController.m
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "RecordDateViewController.h"
#import "RecordDateCell.h"
#import <GoogleMobileAds/GoogleMobileAds.h>

@interface RecordDateViewController ()

@end

@implementation RecordDateViewController
@synthesize market;
NSMutableArray *recordDates;

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
    
    [self getRecordDateList];    
    
    // AdMob Ad
    GADBannerView *bannerView = [[GADBannerView alloc] initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 50)];
    
    self.tableView.tableHeaderView = bannerView;
    
    [UIApplication sharedApplication].networkActivityIndicatorVisible = TRUE;
    bannerView.adUnitID = bannerAdUnitID;
    
    bannerView.rootViewController = self;
    [bannerView loadRequest:[GADRequest request]];

}

-(void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.tabBarController.tabBar.hidden = YES;
}

- (void) getRecordDateList {
    
    NSString *urlString;
    
    if ([market isEqualToString:@"DSE"]) {
        urlString = [NSString stringWithFormat:@"%@dse_agm.php", baseUrl];
    } else if ([market isEqualToString:@"CSE"])  {
        urlString = [NSString stringWithFormat:@"%@cse_agm.php", baseUrl];
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
    recordDates = [NSJSONSerialization JSONObjectWithData:fileData options:kNilOptions error:nil]; // This is JSON Data;

    [alert dismissWithClickedButtonIndex:0 animated:YES];
    
    if ([recordDates count] < 1) {
        UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Message"
                                                            message:@"No result found"
                                                           delegate:self
                                                  cancelButtonTitle:@"OK"
                                                  otherButtonTitles:nil];
        [alertView show];
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
    return [recordDates count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    RecordDateCell *cell = (RecordDateCell *)  [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    // Configure the cell...
    
    cell = [[RecordDateCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
   
    NSString *companyName = [[recordDates objectAtIndex:indexPath.row] valueForKey:@"n"];

    cell.companyName.text =  companyName;
    cell.dividendLabel.text = @"Dividend:";
    cell.agmLabel.text = @"AGM/EGM Date:";
    cell.recordDateLabel.text = @"Record Date:";
    
    NSString * di = [[recordDates objectAtIndex:indexPath.row] valueForKey:@"di"];
    
    if (![di isKindOfClass:[NSNull class]]) {
        cell.dividendText.text = di;
    }
    
    cell.agmText.text = [[recordDates objectAtIndex:indexPath.row] valueForKey:@"da"];
    cell.recordDateText.text = [[recordDates objectAtIndex:indexPath.row] valueForKey:@"rd"];
    

    return (UITableViewCell *) cell;
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

@end
