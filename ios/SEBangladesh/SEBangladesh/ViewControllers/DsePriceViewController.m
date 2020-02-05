//
//  DsePriceViewController.m
//  SEBangladesh
//
//  Created by Dream on 9/14/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "DsePriceViewController.h"
#import "BackgroundLayer.h"
#import "DetailsTableViewController.h"
#import "AppDelegate.h"

@interface DsePriceViewController ()

@end

@implementation DsePriceViewController
@synthesize filteredPriceArray, searcbBar, market;
NSMutableArray *prices;

int dsePriceViews = 0;

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"Details"]) {
        NSString *companyName = [[prices objectAtIndex:[[self.tableView indexPathForSelectedRow] row]] valueForKey:@"n"];
        
        DetailsTableViewController *destViewController = segue.destinationViewController;
        destViewController.name = companyName;
        destViewController.market =market;
        
    } else if  ([[segue identifier] isEqualToString:@"DetailsFiltered"]) {
        NSString *companyName = [[filteredPriceArray objectAtIndex:self.searchDisplayController.searchResultsTableView.indexPathForSelectedRow.row] valueForKey:@"n"];
        
        DetailsTableViewController *destViewController = segue.destinationViewController;
        destViewController.name = companyName;
        destViewController.market = market;
    }
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
    
    [self getCompanyList];
    
    self.tabBarController.tabBar.hidden = YES;

    self.interstitial = [self createAndLoadInterstitial];
    


    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
}


-(void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.tabBarController.tabBar.hidden = YES;
}


-(void) viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 1 * NSEC_PER_SEC), dispatch_get_main_queue(), ^{
////        [object method];
//    });
    
}

- (GADInterstitial *)createAndLoadInterstitial {
    GADInterstitial *interstitial =
    [[GADInterstitial alloc] initWithAdUnitID:interstitialAdUnitID];
    interstitial.delegate = self;
    [interstitial loadRequest:[GADRequest request]];
    return interstitial;
}

- (void)displayAd {
    if (dsePriceViews%interstitialAdFrequency == 0) {
            dispatch_after(dispatch_time(DISPATCH_TIME_NOW, 2 * NSEC_PER_SEC), dispatch_get_main_queue(), ^{
                if (self.interstitial.isReady) {
                    [self.interstitial presentFromRootViewController:self];
                } else {
                    NSLog(@"Ad wasn't ready");
                }
            });
        
        
        
    }
    dsePriceViews++;
}

- (void) getCompanyList {
    NSString *urlString;
    
    if ([market isEqualToString:@"DSE"]) {
        urlString = [NSString stringWithFormat:@"%@dse.php", baseUrl];
    } else if ([market isEqualToString:@"CSE"]) {
        urlString = [NSString stringWithFormat:@"%@cse.php", baseUrl];
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

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
    if (tableView == self.searchDisplayController.searchResultsTableView) {
        return [filteredPriceArray count];
    } else {
        return [prices count];
    }
    
    return [prices count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    // Configure the cell...
    if (nil == cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
    }
    
    NSString *changeString;
    float change;
    
    
    if (tableView == self.searchDisplayController.searchResultsTableView) {
        NSString *catName = [[[filteredPriceArray objectAtIndex:indexPath.row] valueForKey:@"n"] stringByReplacingOccurrencesOfString:@"-" withString:@" "];
        [cell.textLabel setText:catName];
        
        NSString *subTitle = [[NSString alloc] initWithFormat:@"%@ (%@%%)", [[filteredPriceArray objectAtIndex:indexPath.row] valueForKey:@"l"], [[filteredPriceArray objectAtIndex:indexPath.row] valueForKey:@"pc"]];
        cell.detailTextLabel.text = subTitle;
        changeString = [[filteredPriceArray objectAtIndex:indexPath.row] valueForKey:@"c"];

        change = [changeString floatValue];
        
        
        
        
    } else {
        NSString *catName = [[[prices objectAtIndex:indexPath.row] valueForKey:@"n"]  stringByReplacingOccurrencesOfString:@"-" withString:@" "];
        
        
        [cell.textLabel setText:catName];
        NSString *subTitle = [[NSString alloc] initWithFormat:@"%@ (%@%%)", [[prices objectAtIndex:indexPath.row] valueForKey:@"l"], [[prices objectAtIndex:indexPath.row] valueForKey:@"pc"]];
        
        
        cell.detailTextLabel.text = subTitle;
        changeString = [[prices objectAtIndex:indexPath.row] valueForKey:@"c"];
        
        
        change = [changeString floatValue];
        
        
    }
    
    if ([changeString rangeOfString:@"-"].location != NSNotFound) {
        cell.imageView.image = [UIImage imageNamed:@"down_arrow.png"];
    } else if (change > 0) {
        cell.imageView.image = [UIImage imageNamed:@"up_arrow.png"];
    } else {
        cell.imageView.image = [UIImage imageNamed:@"double_headed_arrow.png"];
    }
    
    
    
    return cell;

}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    
    if (tableView == self.searchDisplayController.searchResultsTableView) {
        NSLog(@"Filtered SELECTED");
        [self performSegueWithIdentifier:@"DetailsFiltered" sender:tableView];
        
    } else {
        [self performSegueWithIdentifier:@"Details" sender:tableView];
        NSLog(@"NO FILTERED SELECTED");
    }

    
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
}


#pragma mark - Download Bar
- (void)downloadBar:(UIDownloadBar *)downloadBar didFinishWithData:(NSData *)fileData suggestedFilename:(NSString *)filename {
    //  NSLog(@"%@", filename);
    //    NSLog(@"%@",fileData);
    
    [self displayAd];
    
    prices = [NSJSONSerialization JSONObjectWithData:fileData options:kNilOptions error:nil]; // This is JSON Data;

    
    self.filteredPriceArray = [NSMutableArray arrayWithCapacity:[prices count]];
    
    [self.tableView reloadData];
    [alert dismissWithClickedButtonIndex:0 animated:YES];
    
    if ([prices count] < 1) {
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



#pragma mark - Search Facility
-(void)filterContentForSearchText:(NSString*)searchText scope:(NSString*)scope {
    // Update the filtered array based on the search text and scope.
    // Remove all objects from the filtered search array
    [self.filteredPriceArray removeAllObjects];
    // Filter the array using NSPredicate
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF.n contains[c] %@",searchText];
    filteredPriceArray = [NSMutableArray arrayWithArray:[prices filteredArrayUsingPredicate:predicate]];
}


#pragma mark - UISearchDisplayController Delegate Methods
-(BOOL)searchDisplayController:(UISearchDisplayController *)controller shouldReloadTableForSearchString:(NSString *)searchString {
    // Tells the table data source to reload when text changes
    [self filterContentForSearchText:searchString scope:
     [[self.searchDisplayController.searchBar scopeButtonTitles] objectAtIndex:[self.searchDisplayController.searchBar selectedScopeButtonIndex]]];
    // Return YES to cause the search result table view to be reloaded.
    return YES;
}

-(BOOL)searchDisplayController:(UISearchDisplayController *)controller shouldReloadTableForSearchScope:(NSInteger)searchOption {
    // Tells the table data source to reload when scope bar selection changes
    [self filterContentForSearchText:self.searchDisplayController.searchBar.text scope:
     [[self.searchDisplayController.searchBar scopeButtonTitles] objectAtIndex:searchOption]];
    // Return YES to cause the search result table view to be reloaded.
    return YES;
}

- (IBAction)refreshAction:(id)sender {
    
    [self getCompanyList];
    
}

// Interristrial Ad

- (void)interstitialDidDismissScreen:(GADInterstitial *)interstitial {
    self.interstitial = [self createAndLoadInterstitial];
}
@end
