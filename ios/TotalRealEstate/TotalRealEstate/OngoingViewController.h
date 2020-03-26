//
//  OngoingViewController.h
//  TotalRealEstate
//
//  Created by Dream on 6/24/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ReaderViewController.h"
#import "UIDownloadBar.h"

@interface OngoingViewController : UIViewController <UIAlertViewDelegate, UIWebViewDelegate, ReaderViewControllerDelegate, UIDownloadBarDelegate, UIAlertViewDelegate> {
        UIDownloadBar *bar;
        UILabel *lblForDisplay;
        UIAlertView *alert;
}


@property (weak, nonatomic) IBOutlet UIWebView *webView;
@property (strong, nonatomic) IBOutlet UIBarButtonItem *brochureBtn;

- (IBAction)openBrochure:(id)sender;



@end
