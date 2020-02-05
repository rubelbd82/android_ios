//
//  DseIndexesViewController.m
//  SEBangladesh
//
//  Created by Dream on 9/16/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "DseIndexesViewController.h"

@interface DseIndexesViewController ()

@end

@implementation DseIndexesViewController
@synthesize scrollView;
@synthesize wv1, wv2, wv3, wv4, wv5;
@synthesize bannerView;
UIActivityIndicatorView *indicator;

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
    
    
    int screenHeight = [[UIScreen mainScreen] bounds].size.height;
    scrollView.frame = CGRectMake(0, 0, 320, screenHeight);
    //---set the content size of the scroll view---
    [scrollView setContentSize:CGSizeMake(320, 870)];
    
    
    NSString *urlAddress1;
    NSString *urlAddress2;
    NSString *urlAddress3;
    NSString *urlAddress4;
    NSString *urlAddress5;
    
//    UIUserInterfaceIdiom idiom = [[UIDevice currentDevice] userInterfaceIdiom];
//    if (idiom == UIUserInterfaceIdiomPad)
//    {
        urlAddress1 = [NSString stringWithFormat:@"%@dse_indexes.php?t=1", baseUrl];
        urlAddress2 = [NSString stringWithFormat:@"%@dse_indexes.php?t=2", baseUrl];
        urlAddress3 = [NSString stringWithFormat:@"%@dsex_index.html", baseUrl];
        urlAddress4 = [NSString stringWithFormat:@"%@dgen_index.html", baseUrl];
        urlAddress5 = [NSString stringWithFormat:@"%@ds30_index.html", baseUrl];
       // NSString *urlAddress = @"http://www.google.com";
    
    wv1.delegate = self;
    wv2.delegate = self;
    wv3.delegate = self;
    wv4.delegate = self;
    wv5.delegate = self;
    
    //Create a URL object.
    NSURL *url1 = [NSURL URLWithString:urlAddress1];
    NSURL *url2 = [NSURL URLWithString:urlAddress2];
    NSURL *url3 = [NSURL URLWithString:urlAddress3];
    NSURL *url4 = [NSURL URLWithString:urlAddress4];
    NSURL *url5 = [NSURL URLWithString:urlAddress5];
    
    //URL Requst Object
    NSURLRequest *requestObj1 = [NSURLRequest requestWithURL:url1];
    NSURLRequest *requestObj2 = [NSURLRequest requestWithURL:url2];
    NSURLRequest *requestObj3 = [NSURLRequest requestWithURL:url3];
    NSURLRequest *requestObj4 = [NSURLRequest requestWithURL:url4];
    NSURLRequest *requestObj5 = [NSURLRequest requestWithURL:url5];
    
    //Load the request in the UIWebView.
    [wv1 loadRequest:requestObj1];
    [wv2 loadRequest:requestObj2];
    [wv3 loadRequest:requestObj3];
    [wv4 loadRequest:requestObj4];
    [wv5 loadRequest:requestObj5];
    
    
    
    // indicator = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    
    indicator = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    indicator.frame = CGRectMake(0.0, 0.0, 80.0, 80.0);
    indicator.center = self.view.center;
    [self.view addSubview:indicator];
    [indicator bringSubviewToFront:self.view];
    
    
    [UIApplication sharedApplication].networkActivityIndicatorVisible = TRUE;
    
    // AdMob Ad
    bannerView.adUnitID = bannerAdUnitID;

    bannerView.rootViewController = self;
    [self.bannerView loadRequest:[GADRequest request]];

    
}

-(void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.tabBarController.tabBar.hidden = YES;
}




- (void)webViewDidStartLoad:(UIWebView *)webView {
    NSLog(@"Started");
    [indicator startAnimating];
}

- (void)webViewDidFinishLoad:(UIWebView *)webView {
    NSLog(@"Finished");
    [indicator stopAnimating];
    
}

- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error {
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Error"
                                                        message:@"No Internet Connection"
                                                       delegate:self
                                              cancelButtonTitle:@"OK"
                                              otherButtonTitles:nil];
    [alertView show];
    
    NSLog(@"Download Error: %@", error);
    
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setWv1:nil];
    [self setWv2:nil];
    [self setWv3:nil];
    [self setWv4:nil];
    [self setWv5:nil];
    [super viewDidUnload];
}
@end
