//
//  WidMainViewController.h
//  StoryBook
//
//  Created by Farhad on 2/21/14.
//  Copyright (c) 2014 Farhad. All rights reserved.
//

#import <UIKit/UIKit.h>
@import AVFoundation;


@interface WidMainViewController : UIViewController <AVAudioPlayerDelegate>
@property (weak, nonatomic) IBOutlet UIImageView *bg;
@property (weak, nonatomic) IBOutlet UIButton *settings;
@property (weak, nonatomic) IBOutlet UIButton *readItMyself;
@property (weak, nonatomic) IBOutlet UIButton *readToMe;
@property (weak, nonatomic) IBOutlet UIButton *autoPlay;
@property (weak, nonatomic) IBOutlet UIButton *backToHome;
@property (weak, nonatomic) IBOutlet UIButton *play;
@property (weak, nonatomic) IBOutlet UIButton *previous;
@property (weak, nonatomic) IBOutlet UIButton *next;
@property (weak, nonatomic) IBOutlet UIView *homeButtonContainer;
@property (weak, nonatomic) IBOutlet UILabel *subtitle;


@property (nonatomic, strong) AVAudioPlayer *audioPlayer;

// Actions
- (IBAction)doBtnReadItMyself:(id)sender;
- (IBAction)doBtnReadToMe:(id)sender;
- (IBAction)doBtnAutoPlay:(id)sender;

- (IBAction)doBtnSettings:(id)sender;

- (IBAction)doBtnPlay:(id)sender;
- (IBAction)doBtnBackToHome:(id)sender;
- (IBAction)doBtnPrevious:(id)sender;
- (IBAction)doBtnNext:(id)sender;


@end
