//
//  HomeViewController.m
//  TotalRealEstate
//
//  Created by Dream on 6/14/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import "HomeViewController.h"

@interface HomeViewController ()

@end

@implementation HomeViewController
@synthesize scrollContainer;

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
    
    int screenHeight = [[UIScreen mainScreen] bounds].size.height;
    
    [super viewDidLoad];
    
    scrollContainer.frame = CGRectMake(0, 0, 320, screenHeight);
    
    //---set the content size of the scroll view---
    [scrollContainer setContentSize:CGSizeMake(320, 800)];
    
  //  [[self navigationController] setNavigationBarHidden:YES animated:YES];
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
