//
//  NewsViewController.h
//  TotalRealEstate
//
//  Created by Dream on 7/3/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface NewsViewController : UIViewController <UIAlertViewDelegate, UIWebViewDelegate>
@property (weak, nonatomic) IBOutlet UIWebView *webView;

@end
