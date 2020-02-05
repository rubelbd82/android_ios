//
//  DsePriceViewController.h
//  SEBangladesh
//
//  Created by Dream on 9/14/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIDownloadBar.h"
#import <GoogleMobileAds/GoogleMobileAds.h>

@interface DsePriceViewController : UITableViewController <UIDownloadBarDelegate ,UIAlertViewDelegate, GADInterstitialDelegate> {
    UIDownloadBar *bar;
    UILabel *lblForDisplay;
    UIAlertView *alert;
}

@property (strong, nonatomic) NSString *price;

@property (strong,nonatomic) NSMutableArray *filteredPriceArray;

@property (strong, nonatomic) NSString *market;

@property (weak, nonatomic) IBOutlet UISearchBar *searcbBar;

@property(nonatomic, strong) GADInterstitial *interstitial;


- (IBAction)refreshAction:(id)sender;

@end
