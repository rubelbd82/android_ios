//
//  RecordDateViewController.h
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIDownloadBar.h"

@interface RecordDateViewController : UITableViewController <UIDownloadBarDelegate ,UIAlertViewDelegate> {
    UIDownloadBar *bar;
    UILabel *lblForDisplay;
    UIAlertView *alert;
}

@property (strong, nonatomic) NSString *market;

@end
