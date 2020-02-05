//
//  DetailsTableViewController.h
//  SEBangladesh
//
//  Created by Dream on 9/14/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIDownloadBar.h"

@interface DetailsTableViewController : UITableViewController <UIDownloadBarDelegate ,UIAlertViewDelegate> {
    UIDownloadBar *bar;
    UILabel *lblForDisplay;
    UIAlertView *alert;
}

@property (strong, nonatomic) NSString *name;
@property (strong, nonatomic) NSString *market;

- (IBAction)refreshAction:(id)sender;

@end
