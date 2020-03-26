//
//  WidMainViewController.m
//  StoryBook
//
//  Created by Farhad on 2/21/14.
//  Copyright (c) 2014 Farhad. All rights reserved.
//

#import "WidMainViewController.h"
#import "WidSettings.h"

@interface WidMainViewController ()

@end

@implementation WidMainViewController
@synthesize bg, readItMyself, readToMe, autoPlay, settings, audioPlayer, backToHome, play, next, previous, subtitle, homeButtonContainer;

typedef enum {
    kCoverPage,
    kBackCoverPage,
    kReadItMyself,
    kReadToMe,
    kAutoPlay
} screenType;

screenType screenName = kCoverPage;
screenType tempScreenName;

int initialPageNumber;
int pageNumber;
int totalPages;
BOOL playAudio = NO;
BOOL paused = NO;
BOOL tapped = NO;

NSString *imageName;
NSString *audioName;
NSString *subtitleText;
NSString *imageExtension;
NSString *audioExtension;

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
    [WidSettings installApp];

    initialPageNumber = [WidSettings startsFrom];
    pageNumber = initialPageNumber;
    totalPages = [WidSettings totalPages];

    screenName = kCoverPage;


    [self initialSetup];


    // Add Tap, swipe left, swipe right
    // Tap
    UITapGestureRecognizer *tapper = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapped:)];
    [self.view addGestureRecognizer:tapper];

    // Swipe Left
    UISwipeGestureRecognizer *swipeLeft = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(swipedLeft:)];
    swipeLeft.direction = UISwipeGestureRecognizerDirectionLeft;

    [self.view addGestureRecognizer:swipeLeft];


    // Swipe Right
    UISwipeGestureRecognizer *swipeRight = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(swipedRight:)];

    [self.view addGestureRecognizer:swipeRight];

}


-(void) initialSetup {
    [self setResources];
    bg.image = [UIImage imageNamed:imageName];

    [self setCoverPageButtons];
}

-(void) tapped: (UIGestureRecognizer *) sender {
    if (screenName == kCoverPage || paused) {
        // Do nothing
    } else {
        if (tapped) {
            [self setUntappedScreenButtons];
            tapped = NO;
        } else {
            [self setTappedScreenButtons];
            tapped = YES;
        }
    }
}

-(void) swipedLeft: (UIGestureRecognizer *) sender {

    if (screenName != kCoverPage && screenName !=kBackCoverPage && !paused) {
        [self turnPageNext];
    }
}

-(void) swipedRight: (UIGestureRecognizer *) sender {
    if (screenName != kCoverPage && !paused) {
        [self turnPagePrevious];
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - Button Actions

- (IBAction)doBtnReadItMyself:(id)sender {
    screenName = kReadItMyself;
    [self turnPageNext];
}

- (IBAction)doBtnReadToMe:(id)sender {
    screenName = kReadToMe;
    [self turnPageNext];
}

- (IBAction)doBtnAutoPlay:(id)sender {
    screenName = kAutoPlay;
    if ([WidSettings fileExists:audioName ofType:audioExtension]) {
        [self playAudio];
    } else {
        [self turnPageNext];
    }
}

- (IBAction)doBtnSettings:(id)sender {
}

- (IBAction)doBtnPlay:(id)sender {

    if (!paused) {
        [play setImage:[UIImage imageNamed:@"play.png"] forState:UIControlStateNormal];
        paused = YES;

        if (screenName == kAutoPlay || screenName == kReadToMe) {
            [self stopAudio];
        }
    } else {
        [play setImage:[UIImage imageNamed:@"pause.png"] forState:UIControlStateNormal];
        tapped = NO;
        paused = NO;
        [self setUntappedScreenButtons];

        if (screenName == kAutoPlay || screenName == kReadToMe) {
            [self playAudio];
        }
    }


}

- (IBAction)doBtnBackToHome:(id)sender {
    pageNumber = initialPageNumber + 1;
    [self turnPagePrevious];
}

- (IBAction)doBtnPrevious:(id)sender {
    [self turnPagePrevious];
}

- (IBAction)doBtnNext:(id)sender {
    [self turnPageNext];
}



#pragma mark - Set Methods

-(void)setResources {

    // 1 - Set Image
    UIDevice* device = [UIDevice currentDevice];
    if(device.userInterfaceIdiom == UIUserInterfaceIdiomPad)
    {
        // iPad
        [self setResourcesForiPad];
    }
    else
    {
        // iPhone
        [self setResourcesForiPhone];
    }

    // detect iPhone or iPad


    // 2 - audio name
    audioName = [[NSString alloc] initWithFormat:@"page%i", pageNumber];
    NSString *subtitleName = [[NSString alloc] initWithFormat:@"page%i", pageNumber];
    // 3 - set subtitle
    subtitleText = NSLocalizedString(subtitleName, nil);


}


-(void) setResourcesForiPhone {
    // Add resource names
    // 3 - image name
    imageName = [[NSString alloc] initWithFormat:@"page%i.%@", pageNumber, imageExtension];
}
- (void) setResourcesForiPad {
    // Add resource names
    // 3 - image name
    imageName = [[NSString alloc] initWithFormat:@"ipad-page%i.%@", pageNumber, imageExtension];
}



-(void) hideButtons {
    // decide which buttons will be displayed and which will not.

    // 1 - Hide all buttons
    homeButtonContainer.hidden = YES;
    settings.hidden = YES;

    backToHome.hidden = YES;
    play.hidden = YES;
    next.hidden = YES;
    previous.hidden = YES;
    // subtitle.hidden = YES;

}

-(void) setCoverPageButtons {
    [self hideButtons];

    homeButtonContainer.hidden = NO;
    settings.hidden = NO;
}

-(void) setPlayerScreenButtons {
    [self hideButtons];

    if (screenName == kReadItMyself) {
        next.hidden = NO;
        previous.hidden = NO;
    } else if (screenName == kReadToMe) {
        // do nothing at this moment
    } else if (screenName == kAutoPlay) {
        // do nothing at this moment
    }
}

// Waiting to delete
//-(void) setPlayerPausedScreenButtons {
//    [self hideButtons];
//
//
//    if (screenName == kReadItMyself) {
//        // settings, play, backToHome, previous, next
//        play.hidden = NO;
//        next.hidden = NO;
//        previous.hidden = NO;
//        settings.hidden = NO;
//        subtitle.hidden = NO;
//        backToHome.hidden = NO;
//    }  else if (screenName == kReadToMe) {
//
//        // settings, play, backToHome, settings
//        play.hidden = NO;
//        settings.hidden = NO;
//        subtitle.hidden = NO;
//        backToHome.hidden = NO;
//
//    } else if (screenName == kAutoPlay) {
//        // settings, play, backToHome, settings
//        play.hidden = NO;
//        settings.hidden = NO;
//        subtitle.hidden = NO;
//        backToHome.hidden = NO;
//    }
//
//}

-(void) setBackCoverPageButtons {
   // [self hideButtons];
    [play setImage:[UIImage imageNamed:@"pause.png"] forState:UIControlStateNormal];
    backToHome.hidden = NO;
    play.enabled = NO;

}


-(void) setTappedScreenButtons {
    [play setImage:[UIImage imageNamed:@"pause.png"] forState:UIControlStateNormal];
    if (screenName == kReadItMyself) {
        // settings, play, backToHome, previous, next
        play.hidden = NO;
        next.hidden = NO;
        previous.hidden = NO;
        settings.hidden = NO;
        subtitle.hidden = NO;
        backToHome.hidden = NO;
        play.enabled = NO;
    } else if (screenName == kReadToMe) {
        play.hidden = NO;
        backToHome.hidden = NO;
    } else if (screenName == kAutoPlay) {
        // settings, play, backToHome, settings
        play.hidden = NO;
        backToHome.hidden = NO;
    } else if (screenName == kBackCoverPage) {
        backToHome.hidden = NO;
        play.hidden = NO;
        play.enabled = NO;
    }

}


-(void) setUntappedScreenButtons {
    [self hideButtons];
    if (screenName == kReadItMyself) {
        // settings, play, backToHome, previous, next
        next.hidden = NO;
        previous.hidden = NO;
        subtitle.hidden = NO;
    } else if (screenName == kReadToMe) {
        subtitle.hidden = NO;
    } else if (screenName == kAutoPlay) {
        // settings, play, backToHome, settings
        subtitle.hidden = NO;
    }


}

-(void) turnThePage : (BOOL) directionNext {
    [self hideButtons];
    tapped = NO;

    if (pageNumber == initialPageNumber) {
        screenName = kCoverPage;
    } else if (pageNumber == totalPages) {
        tempScreenName = screenName;
        screenName = kBackCoverPage;
    }


    subtitle.hidden = YES;
    [self setResources];
    // 2 - set resources

    [self turnPageSound];
    UIImage *secondImage = [UIImage imageNamed:imageName];
    [UIView transitionWithView:bg duration:0.5
                       options:(directionNext)?UIViewAnimationOptionTransitionCurlUp:UIViewAnimationOptionTransitionCurlDown animations:^{
                           bg.image = secondImage;
                       } completion:^(BOOL finished) {
                           //  Do whatever when the animation is finished

                           if (screenName == kCoverPage) {
                               [self setCoverPageButtons];
                           } else if (screenName == kBackCoverPage){
                               [self setBackCoverPageButtons];
                               [self playAudio];
                           } else {
                               [self setSubtitle];


                           }
                       }];
}
-(void) turnPageNext {
    // 1 - increase page number
    pageNumber++;
    [self turnThePage:YES];
}


-(void) turnPagePrevious {
    // 1 - increase page number
    if (screenName == kBackCoverPage) {
        screenName = tempScreenName;
    }

    pageNumber--;
    [self turnThePage:NO];
}


#pragma mark -
#pragma mark - Animation

-(void) setSubtitle {
    subtitle.text = subtitleText;
    subtitle.hidden = NO;
    subtitle.alpha = 0.0f;

    // Then fades it away after 2 seconds (the cross-fade animation will take 0.5s)
    
    [UIView animateWithDuration:0.5 delay:0.5 options:0 animations:^{
        // Animate the alpha value of your imageView from 1.0 to 0.0 here
        subtitle.alpha = 1.0f;

    } completion:^(BOOL finished) {

        [self setPlayerScreenButtons];


        if (screenName == kReadToMe || screenName == kAutoPlay) {

            // set the player here...
            [self playAudio];

        }


    }];

    
}


#pragma mark - Audio Player

-(void) playAudio {

    NSBundle *mainBundle = [NSBundle mainBundle];
    NSString *filePath = [mainBundle
                          pathForResource:audioName ofType:audioExtension];

    NSData *fileData = [NSData dataWithContentsOfFile:filePath];
    NSError *error = nil;

    audioPlayer = [[AVAudioPlayer alloc] initWithData:fileData
                                                     error:&error];

    if (error) {
        NSLog(@"The error is: %@", [error description]);
    }

    [audioPlayer prepareToPlay];
    audioPlayer.delegate = self;

    [audioPlayer play];


}


-(void) turnPageSound {
    NSBundle *mainBundle = [NSBundle mainBundle];
    NSString *filePath = [mainBundle
                          pathForResource:@"TurnPage" ofType:@"mp3"];
    NSData *fileData = [NSData dataWithContentsOfFile:filePath];
    NSError *error = nil;

    audioPlayer = [[AVAudioPlayer alloc] initWithData:fileData
                                                error:&error];
    [audioPlayer prepareToPlay];
    // audioPlayer.delegate = self; don't delegate as it trigger audioDidFinishPlaying

    [audioPlayer play];
}

-(void) rewindAudio {

}

-(void) stopAudio {

    [audioPlayer stop];

}


#pragma - Audio Player Delegate

- (void)audioPlayerDidFinishPlaying:(AVAudioPlayer *)player successfully:(BOOL)flag {
    if (screenName == kAutoPlay && !paused) {
        [self turnPageNext];
    }
}



@end
