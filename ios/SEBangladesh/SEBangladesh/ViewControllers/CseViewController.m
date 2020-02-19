//
//  CseViewController.m
//  SEBangladesh
//
//  Created by Dream on 9/14/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "CseViewController.h"
#import "BackgroundLayer.h"
#import "NewsViewController.h"
#import "DsePriceViewController.h"
#import "RecordDateViewController.h"
#import "FavoriteViewController.h"

@interface CseViewController ()

@end

@implementation CseViewController

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"News"]){
        NewsViewController *destViewController = segue.destinationViewController;
        destViewController.market =@"CSE";
    } else if ([[segue identifier] isEqualToString:@"Price"]) {
        DsePriceViewController *destViewController = segue.destinationViewController;
        destViewController.market =@"CSE";
    }  else if ([[segue identifier] isEqualToString:@"RecordDate"]) {
        RecordDateViewController *destViewController = segue.destinationViewController;
        destViewController.market =@"CSE";
    } else if ([[segue identifier] isEqualToString:@"Favorite"]) {
        FavoriteViewController *destViewController = segue.destinationViewController;
        destViewController.market =@"CSE";
    }

}

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
	// Do any additional setup after loading the view.
}

- (void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:YES];
    CAGradientLayer *bgLayer = [BackgroundLayer greenGradient];
    bgLayer.frame = self.view.bounds;
    [self.view.layer insertSublayer:bgLayer atIndex:0];
    
    self.tabBarController.tabBar.hidden = NO;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
