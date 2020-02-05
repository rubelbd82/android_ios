//
//  NewsDetailsViewController.h
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <GoogleMobileAds/GoogleMobileAds.h>

@interface NewsDetailsViewController : UIViewController

@property (strong, nonatomic) NSString *market;
@property (strong, nonatomic) NSString *newsTitle;
@property (strong, nonatomic) NSString *newsDetails;

@property (weak, nonatomic) IBOutlet UILabel *newsTitleLabel;

@property (weak, nonatomic) IBOutlet UIWebView *newsDetailsView;
@property (weak, nonatomic) IBOutlet GADBannerView *bannerView;


@end
