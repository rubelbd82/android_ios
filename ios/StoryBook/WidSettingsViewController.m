//
//  WidSettingsViewController.m
//  StoryBook
//
//  Created by Farhad on 3/1/14.
//  Copyright (c) 2014 Farhad. All rights reserved.
//

#import "WidSettingsViewController.h"
#import "WidSettings.h"
#import "WidWebViewController.h"

@interface WidSettingsViewController ()

@end

@implementation WidSettingsViewController

settingTitle itemSelected;




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
    return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    if (section == 0) {
        return 4;
    } else if (section == 1) {
        return 3;
    } else if (section == 2) {
        return 2;
    }

    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{

    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];

    // Configure the cell...
    if (nil == cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
    }


    if (indexPath.section == 0) {
        switch (indexPath.row) {
            case 0:
                [cell.textLabel setText:@"Browse More Books"];
                break;
            case 1:
                [cell.textLabel setText:@"Tell a friend..."];
                break;
            case 2:
                [cell.textLabel setText:@"Subscribe to iStoryTime Newsletter"];
                break;
            case 3:
                [cell.textLabel setText:@"Contact Support"];
                break;
        }

    } else if (indexPath.section == 1) {
        switch (indexPath.row) {
            case 0: {
                [cell.textLabel setText:@"Narrator Options"];
                UISwitch *narratorOption = [[UISwitch alloc] initWithFrame:CGRectMake(227, 8, 79, 27)];
                [narratorOption addTarget:self action:@selector(narratorOptionChanged:) forControlEvents:UIControlEventValueChanged];
                cell.accessoryView = narratorOption;
                cell.selectionStyle = UITableViewCellSelectionStyleNone;
                if ([WidSettings narratorOption]) {
                    [narratorOption setOn:YES];
                } else {
                    [narratorOption setOn:NO];
                }
                break;
            }
            case 1: {
                [cell.textLabel setText:@"Auto Page Turn"];
                UISwitch *autoPageTurn = [[UISwitch alloc] initWithFrame:CGRectMake(227, 8, 79, 27)];
                [autoPageTurn addTarget:self action:@selector(autoPageTurnChanged:) forControlEvents:UIControlEventValueChanged];
                cell.accessoryView = autoPageTurn;
                cell.selectionStyle = UITableViewCellSelectionStyleNone;

                if ([WidSettings autoPageTurn]) {
                    [autoPageTurn setOn:YES];
                } else {
                    [autoPageTurn setOn:NO];
                }
                break;
            }
            case 2:{
                [cell.textLabel setText:@"Highlight Text"];
                UISwitch *highlightText = [[UISwitch alloc] initWithFrame:CGRectMake(227, 8, 79, 27)];
                [highlightText addTarget:self action:@selector(highlightTextChanged:) forControlEvents:UIControlEventValueChanged];
                cell.accessoryView = highlightText;
                cell.selectionStyle = UITableViewCellSelectionStyleNone;


                if ([WidSettings highlightText]) {
                    [highlightText setOn:YES];
                } else {
                    [highlightText setOn:NO];
                }
                break;
            }
        }

    } else if (indexPath.section == 2) {
        switch (indexPath.row) {
            case 0:
                [cell.textLabel setText:[WidSettings bookTitle]];
                break;
            case 1:
                [cell.textLabel setText:@"About iStoryTime"];
                break;
        }
    }




    return cell;
}


- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    switch (section) {
        case 0:
            return @"";
            break;
        case 1:
            return @"Settings";
            break;
        case 2:
            return @"About";
            break;
        default:
            return nil;
    }
}


#pragma mark - Table view delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{

    if (indexPath.section == 0) {



        switch (indexPath.row) {
            case 0:
                itemSelected = browseMoreBooks;
                [self performSegueWithIdentifier: @"WebViewSegue" sender: self];
                break;
            case 1:
                itemSelected = tellAFriend;
                // Email a friend
                break;
            case 2:
                itemSelected = subscribeToNewsletter;
                // Popup for subscription with only email address box and a button
                break;
            case 3:
                itemSelected = contactSupport;
                [self performSegueWithIdentifier: @"WebViewSegue" sender: self];
            default:
                break;
        }

    } else if (indexPath.section == 1) {
        switch (indexPath.row) {
            case 0:
                itemSelected = narratorOptions;
                //[self performSegueWithIdentifier: @"SelectNarratorSegue" sender: self];
                break;
            case 1:
                itemSelected = autoPageTurn;
                break;
            case 2:
                itemSelected = highlightText;
                break;
            default:
                break;
        }
    } else if (indexPath.section == 2) {
        switch (indexPath.row) {
            case 0:
                itemSelected = aboutTheBook;
                [self performSegueWithIdentifier: @"WebViewSegue" sender: self];
                break;
            case 1:
                itemSelected = aboutiStoryTime;
                [self performSegueWithIdentifier: @"WebViewSegue" sender: self];
            default:
                break;
        }
    }

}



#pragma mark - Switch Delegates


- (void) narratorOptionChanged : (id) sender {
    BOOL state = [sender isOn];

    if (state) {


        [WidSettings setNarratorOption:YES];

    } else {



        [WidSettings setNarratorOption:NO];



    }
    
    
}

- (void) autoPageTurnChanged : (id) sender {
    BOOL state = [sender isOn];

    if (state) {
        [WidSettings setAutoPageTurn:YES];

    } else {
        [WidSettings setAutoPageTurn:NO];
    }


}


- (void) highlightTextChanged : (id) sender {
    BOOL state = [sender isOn];

    if (state) {
        [WidSettings setHighlightText:YES];

    } else {
        [WidSettings setHighlightText:NO];
    }
 
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


 #pragma mark - Navigation

 // In a story board-based application, you will often want to do a little preparation before navigation
 - (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
 {

     if ([[segue identifier] isEqualToString:@"WebViewSegue"]) {
         WidWebViewController *destViewController = segue.destinationViewController;
         destViewController.itemSelected = itemSelected;


         

     }

 }





- (IBAction)doneButtonAction:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}
@end
