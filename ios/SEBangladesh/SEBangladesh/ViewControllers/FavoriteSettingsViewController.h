//
//  FavoriteSettingsViewController.h
//  SEBangladesh
//
//  Created by Dream on 9/16/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIDownloadBar.h"

@interface FavoriteSettingsViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, UIDownloadBarDelegate ,UIAlertViewDelegate, UISearchDisplayDelegate, UISearchBarDelegate> {
    UIDownloadBar *bar;
    UILabel *lblForDisplay;
    UIAlertView *alert;
    UISearchDisplayController* searchDisplayController;
}

@property (weak, nonatomic) IBOutlet UITableView *tableView1;

@property (strong, nonatomic) NSString *price;

@property (strong,nonatomic) NSMutableArray *filteredPriceArray;

@property (strong, nonatomic) NSString *market;


@property (weak, nonatomic) IBOutlet UISearchBar *searcbBar;

- (IBAction)cancelAction:(id)sender;
- (IBAction)saveAction:(id)sender;

@end
