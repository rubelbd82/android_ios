//
//  HandoverViewController.h
//  TotalRealEstate
//
//  Created by Dream on 6/24/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//


#import <UIKit/UIKit.h>
#import "UIDownloadBar.h"

@interface HandoverViewController : UIViewController <UIDownloadBarDelegate, UIAlertViewDelegate> {
    UIDownloadBar *bar;
    UILabel *lblForDisplay;
    UIAlertView *alert;
}

@property (weak, nonatomic) IBOutlet UIWebView *webView;

@end
