//
//  ContactViewController.m
//  TotalRealEstate
//
//  Created by Dream on 6/15/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import "ContactViewController.h"

@interface ContactViewController ()

@end

@implementation ContactViewController
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
    int screenHeight1 = [[UIScreen mainScreen] bounds].size.height;
    
    [super viewDidLoad];
    
    scrollContainer.frame = CGRectMake(0, 120, 320, screenHeight1);
    
    //---set the content size of the scroll view---
    [scrollContainer setContentSize:CGSizeMake(320, 500)];

	// Do any additional setup after loading the view.
}


- (IBAction)dialAction:(id)sender {

    
    NSString *phone = [(UIButton *)sender currentTitle];
    NSString *newPhoneCall = phone;
    
    
    newPhoneCall = [newPhoneCall stringByReplacingOccurrencesOfString:@"-"
                                                           withString:@""];
    
    
    NSLog(@"New phone number: %@", newPhoneCall);
    NSString *phoneNumber = [@"telprompt://" stringByAppendingString:newPhoneCall];
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:phoneNumber]];
    
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
