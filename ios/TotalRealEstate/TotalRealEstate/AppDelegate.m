//
//  AppDelegate.m
//  TotalRealEstate
//
//  Created by Dream on 6/14/13.
//  Copyright (c) 2013 webindream. All rights reserved.
//

#import "AppDelegate.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    UIUserInterfaceIdiom idiom = [[UIDevice currentDevice] userInterfaceIdiom];
    if (idiom == UIUserInterfaceIdiomPad)
    {
        [self customizeiPadTheme];
        
        [self iPadInit];
        //    UINavigationController *navigationController=[[UINavigationController alloc] init];
        
        //   self.NC= navigationController;
        
        //  self.window.rootViewController= self.NC;
        [self customizeiPadTheme];
    }
    else
    {
        [self customizeiPhoneTheme];
        
        //   [self configureiPhoneTabBar];
    }
    
    
    return YES;
}

-(void)customizeiPadTheme {
    
    NSDictionary *textTitleOptions = [NSDictionary dictionaryWithObjectsAndKeys:UIColorFromRGB(0x000000), UITextAttributeTextColor, [UIColor whiteColor], UITextAttributeTextShadowColor, nil];
    
    //        NSDictionary *textTitleOptions = [NSDictionary dictionaryWithObjectsAndKeys:UIColorFromRGB(0x0099ff), UITextAttributeTextColor, [UIColor whiteColor], UITextAttributeTextShadowColor, nil];
    
    [[UINavigationBar appearance] setTitleTextAttributes:textTitleOptions];
    
}

-(void)customizeiPhoneTheme {
    
    
    UIImage *navBarImage = [UIImage imageNamed:@"4-light-menu-bar.png"];
    
    //  [[UINavigationBar appearance] setTitleColor:[UIColor darkGrayColor] forState:UIControlStateNormal];
    
    [[UINavigationBar appearance] setBackgroundImage:navBarImage
                                       forBarMetrics:UIBarMetricsDefault];
    
    
    
    
    UIImage *barButton = [[UIImage imageNamed:@"bar-button.png"] resizableImageWithCapInsets:UIEdgeInsetsMake(0, 5, 0, 5)];
    
    [[UIBarButtonItem appearance] setBackgroundImage:barButton forState:UIControlStateNormal
                                          barMetrics:UIBarMetricsDefault];
    
    UIImage *backButton = [[UIImage imageNamed:@"back.png"] resizableImageWithCapInsets:UIEdgeInsetsMake(0,15,0,6)];
    
    
    [[UIBarButtonItem appearance] setBackButtonBackgroundImage:backButton forState:UIControlStateNormal
                                                    barMetrics:UIBarMetricsDefault];
    
    
    NSDictionary *textTitleOptions = [NSDictionary dictionaryWithObjectsAndKeys:UIColorFromRGB(0x000000), UITextAttributeTextColor, [UIColor whiteColor], UITextAttributeTextShadowColor, nil];
    
//        NSDictionary *textTitleOptions = [NSDictionary dictionaryWithObjectsAndKeys:UIColorFromRGB(0x0099ff), UITextAttributeTextColor, [UIColor whiteColor], UITextAttributeTextShadowColor, nil];
    
    [[UINavigationBar appearance] setTitleTextAttributes:textTitleOptions];
    
    
    NSDictionary *attributes = [NSDictionary dictionaryWithObjectsAndKeys:
                                [UIColor darkGrayColor],
                                UITextAttributeTextColor,
                                [UIColor clearColor],
                                UITextAttributeTextShadowColor, nil];
    
    [[UIBarButtonItem appearance] setTitleTextAttributes: attributes
                                                forState: UIControlStateNormal];
    
    
}


-(void)iPadInit
{
    //    UISplitViewController *splitViewController = (UISplitViewController *)self.window.rootViewController;
    //
    //    splitViewController.delegate = [splitViewController.viewControllers lastObject];
    //
    //
    //    id<MasterViewControllerDelegate> delegate = [splitViewController.viewControllers lastObject];
    //    UINavigationController* nav = [splitViewController.viewControllers objectAtIndex:0];
    //
    //    MasterViewController* master = [nav.viewControllers objectAtIndex:0];
    //
    //    master.delegate = delegate;
    
}


- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
