//
//  FavoriteSettingsViewController.m
//  SEBangladesh
//
//  Created by Dream on 9/16/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "FavoriteSettingsViewController.h"

@interface FavoriteSettingsViewController ()

@property (strong) NSMutableArray *companies;

@end

@implementation FavoriteSettingsViewController
@synthesize filteredPriceArray, searcbBar, market, tableView1;
NSMutableArray *prices;
int totalCompanies;
NSString *market2;

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"Details"]) {
        // Will come to you later...
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


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    [self getCompanyList];
    tableView1.delegate = self;
    tableView1.dataSource = self;
    searchDisplayController.delegate = self;
    searcbBar.delegate = self; 
}

- (void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:YES];
    self.tabBarController.tabBar.hidden = YES;
    
    [self refreshCompanies];
     NSLog(@"MARKET: %@", market);
    
    if ([market isEqualToString:@"DSE"]) {
        market2 = @"Dse";
    } else if ([market isEqualToString:@"CSE"]) {
        market2 = @"Cse";
    }
}

- (BOOL) checkAvailability: (NSString *) companyName {
    for (int i=0; i<[self.companies count]; i++) {
        NSString *currentCompany = [[self.companies objectAtIndex:i] valueForKey:@"companyName"];
        
        if ([companyName isEqualToString:currentCompany]) {
            return YES;
        }
    }
    
        return NO;
}

- (int) indexOfCompany: (NSString *) companyName {
    
    
    for (int i=0; i<[self.companies count]; i++) {
        NSString *currentCompany = [[self.companies objectAtIndex:i] valueForKey:@"companyName"];
        
        if ([companyName isEqualToString:currentCompany]) {
            return i;
        }
    }
    
    return 2147483647;
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
    
    totalCompanies = [self.companies count];

}

- (void) getCompanyList {
    NSString *urlString;
    
    if ([market isEqualToString:@"DSE"]) {
        urlString = [NSString stringWithFormat:@"%@dse.php", baseUrl];
    } else if ([market isEqualToString:@"CSE"]) {
        urlString = [NSString stringWithFormat:@"%@cse.php", baseUrl];
    } else {
        urlString = [NSString stringWithFormat:@"%@dse.php", baseUrl];

    }
    
    NSLog(@"URL: %@", urlString);
    
    
    bar = [[UIDownloadBar alloc] initWithURL:[NSURL URLWithString:urlString]
                            progressBarFrame:CGRectMake(40, 25, 200, 20)
                                     timeout:15
                                    delegate:self];
    alert =[[UIAlertView alloc]init];
    lblForDisplay=[[UILabel alloc]initWithFrame:CGRectMake(90, 40, 200, 20)];
    lblForDisplay.backgroundColor=[UIColor clearColor];
    lblForDisplay.text=@"Please Wait...";
    lblForDisplay.textColor=[UIColor whiteColor];
    [alert addSubview:lblForDisplay];
    [alert addSubview:bar];
    [alert show];
    
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
    if (tableView1 == self.searchDisplayController.searchResultsTableView) {
        return [filteredPriceArray count];
    } else {
        return [prices count];
    }
    return [prices count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView1 dequeueReusableCellWithIdentifier:CellIdentifier];
    
    // Configure the cell...
    if (nil == cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
    }
    
    NSString *catName;
    
    if (tableView1 == self.searchDisplayController.searchResultsTableView) {
        catName = [[[filteredPriceArray objectAtIndex:indexPath.row] valueForKey:@"n"] stringByReplacingOccurrencesOfString:@"-" withString:@" "];
        [cell.textLabel setText:catName];
        

          } else {
        catName = [[[prices objectAtIndex:indexPath.row] valueForKey:@"n"]  stringByReplacingOccurrencesOfString:@"-" withString:@" "];
        
        
        [cell.textLabel setText:catName];
    }
    
    
    if ([self checkAvailability:catName]) {
        
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
    } else {
        cell.accessoryType = UITableViewCellAccessoryNone;
    }
	
    return cell;
    
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    UITableViewCell* cell = [tableView1 cellForRowAtIndexPath:indexPath];
    NSManagedObjectContext *managedObjectContext = [self managedObjectContext];
       
    
    
    if (tableView1 == self.searchDisplayController.searchResultsTableView) {
        NSLog(@"Filtered SELECTED");
        
        if (cell.accessoryType == UITableViewCellAccessoryCheckmark) {
            NSString *companyName = [[filteredPriceArray objectAtIndex:indexPath.row] valueForKey:@"n"];
            
            
            cell.accessoryType = UITableViewCellAccessoryNone;
            int index1 = [self indexOfCompany:companyName];

            
            if (index1 != 2147483647) {
                 [managedObjectContext deleteObject:[self.companies objectAtIndex:index1]];
            }
            
        } else {
            cell.accessoryType = UITableViewCellAccessoryCheckmark;
            NSString *companyName = [[filteredPriceArray objectAtIndex:indexPath.row] valueForKey:@"n"];
            NSManagedObject *newDevice;
            newDevice = [NSEntityDescription insertNewObjectForEntityForName:market2 inManagedObjectContext:managedObjectContext];

            [newDevice setValue:companyName forKey:@"companyName"];            
        }
                
    } else {
        NSLog(@"NO FILTERED SELECTED");
        
        if (cell.accessoryType == UITableViewCellAccessoryCheckmark) {
            NSString *companyName = [[prices objectAtIndex:indexPath.row] valueForKey:@"n"];
            
            
            cell.accessoryType = UITableViewCellAccessoryNone;
            int index1 = [self indexOfCompany:companyName];
            
            
            if (index1 != 2147483647) {
            [managedObjectContext deleteObject:[self.companies objectAtIndex:index1]];
                
            }
        } else {
            cell.accessoryType = UITableViewCellAccessoryCheckmark;
            NSString *companyName = [[prices objectAtIndex:indexPath.row] valueForKey:@"n"];
            NSManagedObject *newDevice;
            newDevice = [NSEntityDescription insertNewObjectForEntityForName:market2 inManagedObjectContext:managedObjectContext];
            
            [newDevice setValue:companyName forKey:@"companyName"];
            
            
        }
    }
    
    NSError *error = nil;
    
    // Save the object to persistent store
    if (![managedObjectContext save:&error]) {
        NSLog(@"Can't Save! %@ %@", error, [error localizedDescription]);
        //
    }
    
    [self refreshCompanies];
    
}


#pragma mark - Download Bar
- (void)downloadBar:(UIDownloadBar *)downloadBar didFinishWithData:(NSData *)fileData suggestedFilename:(NSString *)filename {
    //  NSLog(@"%@", filename);
    
    prices = [NSJSONSerialization JSONObjectWithData:fileData options:kNilOptions error:nil]; // This is JSON Data;

    self.filteredPriceArray = [NSMutableArray arrayWithCapacity:[prices count]];
    
    [tableView1 reloadData];
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

- (IBAction)cancelAction:(id)sender {
    [self dismissModalViewControllerAnimated:YES];
    
}

- (IBAction)saveAction:(id)sender {
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (void)viewDidUnload {
    [self setTableView1:nil];

    [super viewDidUnload];
}
@end
