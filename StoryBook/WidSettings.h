//
//  WidSettings.h
//  StoryBook
//
//  Created by Farhad on 2/25/14.
//  Copyright (c) 2014 Farhad. All rights reserved.
//

#import <Foundation/Foundation.h>

#define WidNarratorOptions @"WidNarratorOptions"
#define WidAutoPageTurn @"WidAutoPageTurn"
#define WidHighlightText @"WidHighlightText"
#define WidInstalled @"WidInstalled"


typedef enum {
    browseMoreBooks,
    tellAFriend,
    subscribeToNewsletter,
    contactSupport,
    narratorOptions,
    autoPageTurn,
    highlightText,
    aboutTheBook,
    aboutiStoryTime
} settingTitle;

@interface WidSettings : NSObject


//FOUNDATION_EXPORT NSString *const coverPage;
//FOUNDATION_EXPORT NSString *const backCoverPage;
//FOUNDATION_EXPORT NSString *const readItMyself;
//FOUNDATION_EXPORT NSString *const readToMe;
//FOUNDATION_EXPORT NSString *const autoPlay;

+(void)installApp;

+(int)startsFrom;
+(int)totalPages;
+(NSString *)bookTitle;
+(NSString *)imageExtension;
+(NSString *)audioExtension;

+(BOOL)narratorOption;
+(void)setNarratorOption:(BOOL)val;

+(BOOL)autoPageTurn;
+(void)setAutoPageTurn:(BOOL)val;

+(BOOL)highlightText;
+(void)setHighlightText:(BOOL)val;

+(BOOL)fileExists:(NSString *)fileName ofType:(NSString *)type;

@end