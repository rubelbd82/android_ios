//
//  ApartmentsViewController.m
//  TotalRealEstate
//
//  Created by Dream on 6/15/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import "ApartmentsViewController.h"

@interface ApartmentsViewController ()

@end

@implementation ApartmentsViewController
@synthesize scrollView;

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
    
    scrollView.frame = CGRectMake(0, 0, 320, 568);
    //---set the content size of the scroll view---
    [scrollView setContentSize:CGSizeMake(320, 1250)];
    
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
