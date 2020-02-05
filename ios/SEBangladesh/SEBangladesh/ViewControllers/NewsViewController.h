//
//  NewsViewController.h
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIDownloadBar.h"
#import <GoogleMobileAds/GoogleMobileAds.h>

@interface NewsViewController : UITableViewController <UIDownloadBarDelegate ,UIAlertViewDelegate, GADInterstitialDelegate> {
    UIDownloadBar *bar;
    UILabel *lblForDisplay;
    UIAlertView *alert;
}

@property (strong, nonatomic) NSString *market;

@property(nonatomic, strong) GADInterstitial *interstitial;

@end
