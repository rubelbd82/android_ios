//
//  WidWebViewController.m
//  StoryBook
//
//  Created by Farhad on 3/1/14.
//  Copyright (c) 2014 Farhad. All rights reserved.
//

#import "WidWebViewController.h"

@interface WidWebViewController ()

@end

@implementation WidWebViewController
@synthesize itemSelected, webView;

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


    //Create a URL object.

    NSString *webViewUrl;

    switch (itemSelected) {
        case browseMoreBooks:
            webViewUrl = @"http://www.istorytime.com/";
            break;
        case contactSupport:
            webViewUrl = @"http://www.istorytime.com/";
            break;
        case aboutTheBook:
            webViewUrl = @"http://www.istorytime.com/";
            break;
        case aboutiStoryTime:
            webViewUrl = @"http://www.istorytime.com/";
            break;

        default:
            break;
    }


    // Activity Indicator

    indicator = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
    [indicator setColor:[UIColor grayColor]];
    indicator.center = CGPointMake(self.view.bounds.size.width / 2.0f, self.view.bounds.size.height / 2.0f);
    indicator.autoresizingMask = (UIViewAutoresizingFlexibleRightMargin | UIViewAutoresizingFlexibleLeftMargin | UIViewAutoresizingFlexibleBottomMargin | UIViewAutoresizingFlexibleTopMargin);


    [self.view addSubview:indicator];
    [indicator bringSubviewToFront:self.view];



    NSURL *url = [NSURL URLWithString:webViewUrl];
    //URL Requst Object

    NSURLRequest *requestObj = [NSURLRequest requestWithURL:url];

    //Load the request in the UIWebView.
    [webView loadRequest:requestObj];

    // 
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - WebView Delegates

- (void) webViewDidStartLoad:(UIWebView *)webView {
    [indicator startAnimating];

    [UIApplication sharedApplication].networkActivityIndicatorVisible = TRUE;
}

- (void) webViewDidFinishLoad:(UIWebView *)webView {
    [indicator stopAnimating];
    [UIApplication sharedApplication].networkActivityIndicatorVisible = FALSE;

}

- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"No Internet" message:@"Please check your internet connection" delegate:self cancelButtonTitle:@"Ok" otherButtonTitles: nil];
    [alert show];
}

- (IBAction)backButtonAction:(id)sender {

    [self dismissViewControllerAnimated:YES completion:nil];
}
@end
