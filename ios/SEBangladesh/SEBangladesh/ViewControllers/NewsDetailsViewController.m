//
//  NewsDetailsViewController.m
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "NewsDetailsViewController.h"

@interface NewsDetailsViewController ()

@end

@implementation NewsDetailsViewController
@synthesize market, newsTitle, newsDetails, newsTitleLabel, newsDetailsView;
@synthesize bannerView;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    newsTitleLabel.text = newsTitle;
    
    NSString *html = [[NSString alloc] initWithFormat:@" <style type='text/css'>p {text-align: justify;font-family: Helvetica, Arial, sans-serif; color: #686868; font-size:14px; }</style><p>%@</p>", newsDetails];

    [newsDetailsView loadHTMLString:html baseURL:nil];

	// Do any additional setup after loading the view.
    
    // AdMob Ad
    [UIApplication sharedApplication].networkActivityIndicatorVisible = TRUE;
    bannerView.adUnitID = bannerAdUnitID;
    
    bannerView.rootViewController = self;
    [self.bannerView loadRequest:[GADRequest request]];
}

-(void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.tabBarController.tabBar.hidden = YES;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setTitle:nil];
    [self setNewsTitleLabel:nil];
    [self setNewsDetailsView:nil];
    [super viewDidUnload];
}
@end
