//
//  AboutViewController.m
//  TotalRealEstate
//
//  Created by Dream on 6/15/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import "AboutViewController.h"

@interface AboutViewController ()

@end

@implementation AboutViewController
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
    
    NSString *html = @"";
    UIUserInterfaceIdiom idiom = [[UIDevice currentDevice] userInterfaceIdiom];
    if (idiom == UIUserInterfaceIdiomPad)
    {
        html = @"<style type='text/css'>p {text-align: justify;font-family: Helvetica, Arial, sans-serif; color: #686868; font-size:24px; }</style><p>The journey began in the year 1997, when <strong>Total Real Estate</strong> was incorporated to undertake construction business. The company was started by <strong>Sadequl Amin Khan</strong>, a first generation company enterpreneur, an <strong>MBA</strong> (<strong>IBA</strong>) by qualification and a visionary. With nearly 2 decades of experience in Real Estate Development, <strong>Sadiqul Amin Khan</strong>, as managing director of <strong>Total Real Estate</strong>, has been at the forefront of the Real Estate Industry through building world-class residential and commercial projects. </p>    <p>In the span of a few years, <strong>Total Real Estate</strong> has experienced exponential growth and success. The company is currently working on multiple Real Estate Projects across the capital. Total Real Estate has progressed with Leaps and bounds in the last decade and is already making it's presence felt across the city. </p>    <p>Today, <strong>Total Real Estate</strong> enjoys Bangladesh's leading real estate developers with an indelible focus on customer satisfaction.<strong> Total Real Estate</strong> has adopted quality system standards that integrate technological and design innovations with a strong technical base to provide estate-of-the-art real estate options. </p>    <p>The uniqueness of <strong>Total Real Estate</strong> lies in the company's ability in ensuring timely completion and also believes in keeping pace with the progress in construction technology, helping to give clients <em>&quot;value for money&quot;</em>. </p>    <p>For <strong>Total Real Estate</strong>, environmental protection and innovative architecture and practices are the standards of today and tomorrow's in real estate development. <br></p><p>&nbsp;</p>";
        
        
        
    } else {
    
    html = @"<style type='text/css'>p {text-align: justify;font-family: Helvetica, Arial, sans-serif; color: #686868; font-size:12px; }</style><p>The journey began in the year 1997, when <strong>Total Real Estate</strong> was incorporated to undertake construction business. The company was started by <strong>Sadequl Amin Khan</strong>, a first generation company enterpreneur, an <strong>MBA</strong> (<strong>IBA</strong>) by qualification and a visionary. With nearly 2 decades of experience in Real Estate Development, <strong>Sadiqul Amin Khan</strong>, as managing director of <strong>Total Real Estate</strong>, has been at the forefront of the Real Estate Industry through building world-class residential and commercial projects. </p>    <p>In the span of a few years, <strong>Total Real Estate</strong> has experienced exponential growth and success. The company is currently working on multiple Real Estate Projects across the capital. Total Real Estate has progressed with Leaps and bounds in the last decade and is already making it's presence felt across the city. </p>    <p>Today, <strong>Total Real Estate</strong> enjoys Bangladesh's leading real estate developers with an indelible focus on customer satisfaction.<strong> Total Real Estate</strong> has adopted quality system standards that integrate technological and design innovations with a strong technical base to provide estate-of-the-art real estate options. </p>    <p>The uniqueness of <strong>Total Real Estate</strong> lies in the company's ability in ensuring timely completion and also believes in keeping pace with the progress in construction technology, helping to give clients <em>&quot;value for money&quot;</em>. </p>    <p>For <strong>Total Real Estate</strong>, environmental protection and innovative architecture and practices are the standards of today and tomorrow's in real estate development. <br></p><p>&nbsp;</p>";
    
        
    }
    
    
    [webView loadHTMLString:html baseURL:nil];
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
