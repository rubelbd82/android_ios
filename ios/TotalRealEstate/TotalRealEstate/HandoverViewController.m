//
//  HandoverViewController.m
//  TotalRealEstate
//
//  Created by Dream on 6/24/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import "HandoverViewController.h"

@interface HandoverViewController ()

@end

@implementation HandoverViewController
@synthesize webView;

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
  
    
    NSString *urlAddress;
    
    UIUserInterfaceIdiom idiom = [[UIDevice currentDevice] userInterfaceIdiom];
    if (idiom == UIUserInterfaceIdiomPad)
    {
        urlAddress = @"http://webindream.com/android/totalgroup/widadmin/display-handover-apartments.php?device=iPad";
        
    } else {
        
        urlAddress = @"http://webindream.com/android/totalgroup/widadmin/display-handover-apartments.php";
    }
    
    
    bar = [[UIDownloadBar alloc] initWithURL:[NSURL URLWithString:urlAddress]
                            progressBarFrame:CGRectMake(40, 25, 200, 20)
                                     timeout:15
                                    delegate:self];
    alert =[[UIAlertView alloc]init];
    lblForDisplay=[[UILabel alloc]initWithFrame:CGRectMake(90, 40, 200, 20)];
    lblForDisplay.backgroundColor=[UIColor clearColor];
    lblForDisplay.text=@"Downloading...";
    lblForDisplay.textColor=[UIColor whiteColor];
    [alert addSubview:lblForDisplay];
    [alert addSubview:bar];
    [alert show];
}

- (void)downloadBar:(UIDownloadBar *)downloadBar didFinishWithData:(NSData *)fileData suggestedFilename:(NSString *)filename {
    
    [alert dismissWithClickedButtonIndex:0 animated:YES];
    [webView loadData:fileData MIMEType: @"text/html" textEncodingName: @"UTF-8" baseURL:nil];
    
}

- (void)downloadBar:(UIDownloadBar *)downloadBar didFailWithError:(NSError *)error {
    [alert dismissWithClickedButtonIndex:0 animated:YES];
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Error"
                                                        message:@"No Internet Connection"
                                                       delegate:self
                                              cancelButtonTitle:@"OK"
                                              otherButtonTitles:nil];
    [alertView show];
    
    
    NSLog(@"Download Error: %@", error);
}

- (void)downloadBarUpdated:(UIDownloadBar *)downloadBar {
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
