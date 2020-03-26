//
//  UpcomingViewController.m
//  TotalRealEstate
//
//  Created by Dream on 6/24/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import "UpcomingViewController.h"


@interface UpcomingViewController ()

@end

@implementation UpcomingViewController
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
        urlAddress = @"http://webindream.com/android/totalgroup/widadmin/display-upcoming-apartments.php?device=iPad";  
    } else {
        urlAddress = @"http://webindream.com/android/totalgroup/widadmin/display-upcoming-apartments.php";
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

    
    
    
    
    //Create a URL object.
//    NSURL *url = [NSURL URLWithString:urlAddress];
//    
//    //URL Requst Object
//    NSURLRequest *requestObj = [NSURLRequest requestWithURL:url];
//    
//    //Load the request in the UIWebView.
//    [webView loadRequest:requestObj];
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

- (void)viewDidUnload {
    [self setWebView:nil];
    [super viewDidUnload];
}
@end
