//
//  FavoriteViewController.h
//  SEBangladesh
//
//  Created by Dream on 9/16/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIDownloadBar.h"

@interface FavoriteViewController : UITableViewController  <UIDownloadBarDelegate ,UIAlertViewDelegate> {
    UIDownloadBar *bar;
    UILabel *lblForDisplay;
    UIAlertView *alert;
}

@property (weak, nonatomic) IBOutlet UIBarButtonItem *info;

@property (strong, nonatomic) NSString *market;

@property (strong,nonatomic) NSMutableArray *filteredPriceArray;

@property (weak, nonatomic) IBOutlet UISearchBar *searcbBar;


- (IBAction)refreshAction:(id)sender;


@end
