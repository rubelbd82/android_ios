//
//  DetailsTableViewController.m
//  SEBangladesh
//
//  Created by Dream on 9/14/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "DetailsTableViewController.h"
#import "CustomCell.h"
#import <GoogleMobileAds/GoogleMobileAds.h>

@interface DetailsTableViewController ()

@end

@implementation DetailsTableViewController
@synthesize name, market;
NSMutableArray *details;

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
    
    
 //   self.tableView.delegate = self;
    
    [self getCompanyList];
    
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

- (void) getCompanyList {
    
    NSString *urlString;
    
    if ([market isEqualToString:@"DSE"]) {
        urlString = [NSString stringWithFormat:@"%@dse_company_details.php?company_name=%@", baseUrl, name];
    } else if ([market isEqualToString:@"CSE"])  {
        urlString = [NSString stringWithFormat:@"%@cse_company_details.php?company_name=%@", baseUrl, name];
    }
    
    
    NSLog(@"URL: %@", urlString);
    
    
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
    //    NSLog(@"%@",fileData);
    
    details = [NSJSONSerialization JSONObjectWithData:fileData options:kNilOptions error:nil]; // This is JSON Data;
    
    [alert dismissWithClickedButtonIndex:0 animated:YES];
    [self.tableView reloadData];
    self.title = name;
    
    NSLog(@"Company Name: %@", [details valueForKey:@"name"]);
    
    if ([details count] < 1) {
        UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Message"
                                                            message:@"No result found"
                                                           delegate:self
                                                  cancelButtonTitle:@"OK"
                                                  otherButtonTitles:nil];
        [alertView show];
    }
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
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    
    if (section == 0) {
        return 1;
    } else {
        return 15;
    }
    return 0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{

    static NSString *CellIdentifier = @"Cell";
    CustomCell *cell = (CustomCell*)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    

        cell = [[CustomCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
   
    
        
    // Configure the cell...
    
    switch (indexPath.section) {
            
        case 0: {
            
            
            switch (indexPath.row) {
                case 0:
                    cell.customLabel.text =  @"Name";
                    cell.customDetailLabel.text = [details valueForKey:@"name"];
                    break;
               
            }
        }
            break;
            
            
        case 1:{
            switch (indexPath.row) {
                    
                case 0:
                    cell.customLabel.text =  @"Last Trade";
                    cell.customDetailLabel.text = [details valueForKey:@"ltp"];
                    
                    
                    break;
                case 1:
                   cell.customLabel.text =  @"Last Update";
                    cell.customDetailLabel.text = [details valueForKey:@"last_update"];
                    break;
                case 2:
                   cell.customLabel.text =  @"Change";
                    cell.customDetailLabel.text = [details valueForKey:@"change"];
                    break;
                case 3:
                    cell.customLabel.text =  @"Percent Change";
                    cell.customDetailLabel.text = [details valueForKey:@"percent_change"];
                    break;
                case 4:
                    cell.customLabel.text =  @"Volume Traded";
                    cell.customDetailLabel.text = [details valueForKey:@"volume_traded"];
                    break;
                case 5:
                    cell.customLabel.text =  @"Day's Range";
                    cell.customDetailLabel.text = [details valueForKey:@"day_range"];
                    break;
                case 6:
                    cell.customLabel.text =  @"52 week's Range";
                    cell.customDetailLabel.text = [details valueForKey:@"year_range"];
                    break;
                case 7:
                    cell.customLabel.text =  @"Face Value";
                    cell.customDetailLabel.text = [details valueForKey:@"face_value"];
                    break;
                case 8:
                    cell.customLabel.text =  @"Market Lot";
                    cell.customDetailLabel.text = [details valueForKey:@"market_lot"];
                    break;
                case 9:
                    cell.customLabel.text =  @"Market Capital";
                    cell.customDetailLabel.text = [details valueForKey:@"market_capital"];
                    break;
                case 10:
                    cell.customLabel.text =  @"Authorized Capital";
                    cell.customDetailLabel.text = [details valueForKey:@"authorized_capital"];
                    break;
                case 11:
                    cell.customLabel.text =  @"Paid-up Capital";
                    cell.detailTextLabel.text = [details valueForKey:@"paidup_capital"];
                    break;
                case 12:
                    cell.customLabel.text =  @"Reserve / Surplus";
                    cell.customDetailLabel.text = [details valueForKey:@"reserve"];
                    break;
                case 13:
                    cell.customLabel.text =  @"Market Category";
                    cell.customDetailLabel.text = [details valueForKey:@"market_category"];
                    break;
                case 14:
                    cell.customLabel.text =  @"P/E Ratio";
                    cell.customDetailLabel.text = [details valueForKey:@"pe_ratio"];
                    break;
                case 15:
                    cell.customLabel.text =  @"Change";
                    cell.customDetailLabel.text = [details valueForKey:@"change"];
                    
                    break;
                default:
                    break;
            }
            
        }
            break;
        default:
            break;
    }
    


    
    
    
    return (UITableViewCell *)cell;
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




- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    
    // The header for the section is the region name -- get this from the region at the section index.
    
    if (section == 0) {
        return @"Company Name";
    } else if (section == 1) {
        return @"Company Details";
    
    }
    
    return @"Company Name";
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (IBAction)refreshAction:(id)sender {
    [self getCompanyList];
}
@end
