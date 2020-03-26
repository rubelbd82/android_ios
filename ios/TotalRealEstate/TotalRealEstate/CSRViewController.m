//
//  CSRViewController.m
//  TotalRealEstate
//
//  Created by Dream on 6/25/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import "CSRViewController.h"

@interface CSRViewController ()

@end

@implementation CSRViewController
@synthesize webView;
int displayWebView1 = 0;
UIActivityIndicatorView *indicator;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:YES];
    displayWebView1 = 0;
}


- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    
    
    
    NSString *urlAddress;
    
    UIUserInterfaceIdiom idiom = [[UIDevice currentDevice] userInterfaceIdiom];
    if (idiom == UIUserInterfaceIdiomPad)
    {
         urlAddress = @"http://webindream.com/android/totalgroup/widadmin/display-csr.php?device=iPad";
    } else {
        urlAddress = @"http://webindream.com/android/totalgroup/widadmin/display-csr.php";
    
    }
    
    // NSString *urlAddress = @"http://www.google.com";
    
    webView.delegate = self;
    
    //Create a URL object.
    NSURL *url = [NSURL URLWithString:urlAddress];
    
    //URL Requst Object
    NSURLRequest *requestObj = [NSURLRequest requestWithURL:url];
    
    //Load the request in the UIWebView.
    [webView loadRequest:requestObj];
    
    
    
    // indicator = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    
    indicator = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    indicator.frame = CGRectMake(0.0, 0.0, 80.0, 80.0);
    indicator.center = self.view.center;
    [self.view addSubview:indicator];
    [indicator bringSubviewToFront:self.view];
    
    [UIApplication sharedApplication].networkActivityIndicatorVisible = TRUE;
    
    
}

- (void)webViewDidStartLoad:(UIWebView *)webView {
    NSLog(@"Started");
    [indicator startAnimating];
    
    
    displayWebView1++;
    
    NSLog(@"WebView Start count: %i", displayWebView1);
    if (displayWebView1  > 0) {
        self.webView.hidden = TRUE;
    }
    
    
}
- (void)webViewDidFinishLoad:(UIWebView *)webView {
    NSLog(@"Finished");
    [indicator stopAnimating];
    
    
    displayWebView1--;
    
    NSLog(@"WebView Finish count: %i", displayWebView1);
    
    if (displayWebView1  == 0) {
        self.webView.hidden = FALSE;
    }
    
    
}
- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error {
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Error"
                                                        message:@"No Internet Connection"
                                                       delegate:self
                                              cancelButtonTitle:@"OK"
                                              otherButtonTitles:nil];
    [alertView show];
    
    
    NSLog(@"Download Error: %@", error);
    
    
    displayWebView1--;
    
    if (displayWebView1  == 0) {
        self.webView.hidden = FALSE;
    }
    
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
