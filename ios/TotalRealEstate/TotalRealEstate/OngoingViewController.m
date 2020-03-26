//
//  OngoingViewController.m
//  TotalRealEstate
//
//  Created by Dream on 6/24/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import "OngoingViewController.h"

@interface OngoingViewController ()

@end

@implementation OngoingViewController
@synthesize webView, brochureBtn;
int displayWebView = 0;
UIActivityIndicatorView *indicator;
NSMutableArray *toolbarButtons;
NSString *pdfUrl;
NSString *storePath = @"";
BOOL brochureDownloaded = NO;

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
    
    toolbarButtons = [self.navigationItem.rightBarButtonItems mutableCopy];
    [self hideBrochure];
    pdfUrl = @"";
    
    NSString *urlAddress;
    
    UIUserInterfaceIdiom idiom = [[UIDevice currentDevice] userInterfaceIdiom];
    if (idiom == UIUserInterfaceIdiomPad)
    {
        urlAddress = @"http://webindream.com/android/totalgroup/widadmin/display-ongoing-apartments.php?device=iPad";
    } else {
        urlAddress = @"http://webindream.com/android/totalgroup/widadmin/display-ongoing-apartments.php";
        
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
    indicator.frame = CGRectMake(0.0, 0.0, 40.0, 40.0);
    indicator.center = self.view.center;
    [self.view addSubview:indicator];
    [indicator bringSubviewToFront:self.view];
    
    [UIApplication sharedApplication].networkActivityIndicatorVisible = TRUE;


}

- (void)webViewDidStartLoad:(UIWebView *)webView {
    NSLog(@"Started");
    [indicator startAnimating];
    
    
    displayWebView++;
    
     NSLog(@"WebView Start count: %i", displayWebView);
    if (displayWebView  > 0) {
       // self.webView.hidden = TRUE;
    }
    
    
}

- (void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:YES];
    displayWebView = 0;
}
- (void)webViewDidFinishLoad:(UIWebView *)webView {
    
    [indicator stopAnimating];
    
    
    displayWebView--;
    
    NSLog(@"WebView Finish count: %i", displayWebView);
    
    if (displayWebView  == 0) {
       // self.webView.hidden = FALSE;
        [self pdfBtn];
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
    
    
}


- (void) pdfBtn {
    NSString *source = [self.webView stringByEvaluatingJavaScriptFromString:@"document.documentElement.outerHTML"];
    NSArray *listItems = [source componentsSeparatedByString:@"!wid#"];
    
   pdfUrl = @"";
    
    if ([listItems count] > 1) {
        pdfUrl = [[listItems objectAtIndex:1] stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    }
    
    if (![pdfUrl isEqualToString:@""]) {
        [self showBrochure];
    } else {
        [self hideBrochure];
    }
}

-(void) hideBrochure {
    [toolbarButtons removeObject:brochureBtn];
    [self.navigationItem setRightBarButtonItems:toolbarButtons animated:YES];
    
}

-(void) showBrochure {
    if (![toolbarButtons containsObject:brochureBtn]) {
        // The following line adds the object to the end of the array.
        // If you want to add the button somewhere else, use the `insertObject:atIndex:`
        // method instead of the `addObject` method.
        [toolbarButtons addObject:brochureBtn];
        [self.navigationItem setRightBarButtonItems:toolbarButtons animated:YES];
    }
}







- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setBrochureBtn:nil];
    [super viewDidUnload];
}
- (IBAction)openBrochure:(id)sender {
    
    NSLog(@"PDF URL: %@", pdfUrl);
    if (!brochureDownloaded) {
        bar = [[UIDownloadBar alloc] initWithURL:[NSURL URLWithString:pdfUrl]
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
    } else {
        [self openReader];
    }
}


- (void) openReader {
    
    
    ReaderDocument *document = [ReaderDocument withDocumentFilePath:storePath password:nil];
    
    if (document != nil)
    {
        ReaderViewController *readerViewController = [[ReaderViewController alloc] initWithReaderDocument:document];
        readerViewController.delegate = self;
        readerViewController.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
        readerViewController.modalPresentationStyle = UIModalPresentationFullScreen;
        [self presentModalViewController:readerViewController animated:YES];
    }
}

- (void)dismissReaderViewController:(ReaderViewController *) viewController
{
    [self dismissModalViewControllerAnimated:YES];
}



- (void)downloadBar:(UIDownloadBar *)downloadBar didFinishWithData:(NSData *)fileData suggestedFilename:(NSString *)filename {
    NSString *applicationDocumentsDir = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) lastObject];
    storePath = [applicationDocumentsDir stringByAppendingPathComponent:filename];
    
    [fileData writeToFile:storePath atomically:TRUE];
    fileData = nil;

    
    BOOL fileExists = [[NSFileManager defaultManager] fileExistsAtPath:storePath];
    
    
    
    
    [alert dismissWithClickedButtonIndex:0 animated:YES];
   // [self openReader:storePath];
    
    if (fileExists) {
        brochureDownloaded = YES;
        [self openReader];
    }
    
     
    
    
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


@end
