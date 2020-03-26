//
//  WidWebViewController.h
//  StoryBook
//
//  Created by Farhad on 3/1/14.
//  Copyright (c) 2014 Farhad. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "WidSettings.h"



@interface WidWebViewController : UIViewController <UIWebViewDelegate, UIAlertViewDelegate>
// Properties & Outlets
@property (nonatomic) settingTitle itemSelected;

@property (weak, nonatomic) IBOutlet UIWebView *webView;




// Actions
- (IBAction)backButtonAction:(id)sender;


@end
