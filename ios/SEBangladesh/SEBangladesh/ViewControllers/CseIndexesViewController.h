//
//  CseIndexesViewController.h
//  SEBangladesh
//
//  Created by Dream on 9/16/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <GoogleMobileAds/GoogleMobileAds.h>

@interface CseIndexesViewController : UIViewController <UIWebViewDelegate> {
    IBOutlet UIScrollView *scrollView;
}
@property (weak, nonatomic) IBOutlet GADBannerView *bannerView;

@property (nonatomic, retain) UIScrollView *scrollView;

@property (weak, nonatomic) IBOutlet UIWebView *wv1;
@property (weak, nonatomic) IBOutlet UIWebView *wv2;
@property (weak, nonatomic) IBOutlet UIWebView *wv3;
@property (weak, nonatomic) IBOutlet UIWebView *wv4;

@end
