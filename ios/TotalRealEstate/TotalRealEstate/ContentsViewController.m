//
//  ContentsViewController.m
//  TotalRealEstate
//
//  Created by Dream on 7/3/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import "ContentsViewController.h"

@interface ContentsViewController ()

@end

@implementation ContentsViewController

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
    
    int screenHeight = [[UIScreen mainScreen] bounds].size.height;
    scrollView.frame = CGRectMake(0, 160, 320, screenHeight);
    
    //---set the content size of the scroll view---
    [scrollView setContentSize:CGSizeMake(250, 578)];
    
    
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
