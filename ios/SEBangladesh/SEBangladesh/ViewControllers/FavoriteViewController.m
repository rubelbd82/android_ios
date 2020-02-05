//
//  FavoriteViewController.m
//  SEBangladesh
//
//  Created by Dream on 9/16/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "FavoriteViewController.h"
#import "FavoriteSettingsViewController.h"
#import "DetailsTableViewController.h"
#import <GoogleMobileAds/GoogleMobileAds.h>

@interface FavoriteViewController ()

@property (strong) NSMutableArray *companies;

@end

@implementation FavoriteViewController
@synthesize info, market, filteredPriceArray, searcbBar;
NSMutableArray *prices;

NSString *market2;


- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"Settings"]){
        FavoriteSettingsViewController *destViewController = segue.destinationViewController;
        destViewController.market =market;
    }  if ([[segue identifier] isEqualToString:@"Details"]) {
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

- (NSManagedObjectContext *)managedObjectContext {
    NSManagedObjectContext *context = nil;
    id delegate = [[UIApplication sharedApplication] delegate];
    if ([delegate performSelector:@selector(managedObjectContext)]) {
        context = [delegate managedObjectContext];
    }
    return context;
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
    
    info = [UIButton buttonWithType:UIButtonTypeInfoDark];
    UIButton* infoButton = [UIButton buttonWithType:UIButtonTypeInfoDark];
	[infoButton addTarget:self action:@selector(infoButtonAction) forControlEvents:UIControlEventTouchUpInside];
	UIBarButtonItem *modalButton = [[UIBarButtonItem alloc] initWithCustomView:infoButton];
	[self.navigationItem setRightBarButtonItem:modalButton animated:YES];
    

    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    // AdMob Ad
    GADBannerView *bannerView = [[GADBannerView alloc] initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 50)];
    
    self.tableView.tableFooterView = bannerView;
    

    [UIApplication sharedApplication].networkActivityIndicatorVisible = TRUE;
    bannerView.adUnitID = bannerAdUnitID;
    
    bannerView.rootViewController = self;
    [bannerView loadRequest:[GADRequest request]];

}

-(void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.tabBarController.tabBar.hidden = YES;
        [self getCompanyList];
}

- (void) refreshCompanies {
    if ([market isEqualToString:@"DSE"]) {
        market2 = @"Dse";
    } else if ([market isEqualToString:@"CSE"]) {
        market2 = @"Cse";
    }
    
    NSManagedObjectContext *managedObjectContext = [self managedObjectContext];
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] initWithEntityName:market2];
    self.companies = [[managedObjectContext executeFetchRequest:fetchRequest error:nil] mutableCopy];
    
}

- (NSString *)  generateUrl {
    
    NSString *urlString;
    
    NSString *newUrl;
    
    if ([market isEqualToString:@"DSE"]) {
        urlString = [NSString stringWithFormat:@"%@dse_fav.php?companies=", baseUrl];
    } else if ([market isEqualToString:@"CSE"]) {
        urlString = [NSString stringWithFormat:@"%@cse_fav.php?companies=", baseUrl];
    }

    NSLog(@"Total Companies %lu", (unsigned long)[self.companies count]);
    
    for (int i=0; i<[self.companies count]; i++) {
         NSString *currentCompany = [[self.companies objectAtIndex:i] valueForKey:@"companyName"];
        newUrl = [[NSString alloc] initWithFormat:@"%@yw%@", newUrl, currentCompany];
    }
    
    
    NSString *newUrl2 = [[NSString alloc] initWithFormat:@"%@%@", urlString, newUrl];
    newUrl2 = [newUrl2 stringByReplacingOccurrencesOfString:@"(null)yw"
                                         withString:@""];

    return newUrl2;
}



- (void) getCompanyList {
    
    [self refreshCompanies];
    
    NSString *urlString;
    urlString = [self generateUrl];
    
    
           
    
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



- (void) infoButtonAction {
    [self performSegueWithIdentifier:@"Settings" sender:self];
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
    
    prices = [NSJSONSerialization JSONObjectWithData:fileData options:kNilOptions error:nil]; // This is JSON Data;
    
    
    self.filteredPriceArray = [NSMutableArray arrayWithCapacity:[prices count]];
    
    [self.tableView reloadData];
    [alert dismissWithClickedButtonIndex:0 animated:YES];
    
    if ([prices count] < 1) {
        UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Message"
                                                            message:@"No result found."
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


- (void)viewDidUnload {
    [self setInfo:nil];
    [super viewDidUnload];
}
@end
