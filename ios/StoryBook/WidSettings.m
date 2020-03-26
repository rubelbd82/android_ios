//
//  WidSettings.m
//  StoryBook
//
//  Created by Farhad on 2/25/14.
//  Copyright (c) 2014 Farhad. All rights reserved.
//

#import "WidSettings.h"

@implementation WidSettings

// Settings Start

NSString *bookTitle = @"The Ugly Duckling";         // The name of the book
int startsFrom = 1;                                 // Number of first page such as 0 or 1
int totalPages = 40;                                // The last page number
NSString *imageExtension = @"jpg";                 // Image file extension, such as png or jpg. Don't use dot (.)
NSString *audioExtension = @"wav";                  // Audio file extension, such as mp3 or wav. Don't use dot (.)

// Settings Ends

+(int) startsFrom {
    return startsFrom;
}

+(int) totalPages {

    return totalPages;
}

+(NSString *) bookTitle {
    return bookTitle;
}

+(NSString *) imageExtension {
    return imageExtension;
}

+(NSString *) audioExtension {
    return audioExtension;
}

// First Install

+(void)installApp {
    if (![[NSUserDefaults standardUserDefaults] boolForKey:WidInstalled]) {
        // Set Everything to true
        [WidSettings setNarratorOption:YES];
        [WidSettings setAutoPageTurn:YES];
        [WidSettings setHighlightText:YES];
        [[NSUserDefaults standardUserDefaults] setBool:YES forKey:WidInstalled];
        [[NSUserDefaults standardUserDefaults] synchronize];
    }
}

// For settings...

+(BOOL)narratorOption {
    return [[NSUserDefaults standardUserDefaults] boolForKey:WidNarratorOptions];
}

+(void)setNarratorOption:(BOOL)val {
    [[NSUserDefaults standardUserDefaults] setBool:val forKey:WidNarratorOptions];
}

+(BOOL)autoPageTurn {
    return [[NSUserDefaults standardUserDefaults] boolForKey:WidAutoPageTurn];
}
+(void)setAutoPageTurn:(BOOL)val {
    [[NSUserDefaults standardUserDefaults] setBool:val forKey:WidAutoPageTurn];
}

+(BOOL)highlightText {
    return [[NSUserDefaults standardUserDefaults] boolForKey:WidHighlightText];
}
+(void)setHighlightText:(BOOL)val {
    [[NSUserDefaults standardUserDefaults] setBool:val forKey:WidHighlightText];
}

+(BOOL)fileExists:(NSString *)fileName ofType:(NSString *)type {
    NSString *pathAndFileName = [[NSBundle mainBundle] pathForResource:fileName ofType:type];
    return [[NSFileManager defaultManager] fileExistsAtPath:pathAndFileName];
}

@end
