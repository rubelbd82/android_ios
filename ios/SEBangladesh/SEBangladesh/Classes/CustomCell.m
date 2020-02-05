//
//  CustomCell.m
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "CustomCell.h"

@implementation CustomCell
@synthesize customLabel, customDetailLabel;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
        
        customLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, 7, 100, 30)];
        customLabel.font = [UIFont boldSystemFontOfSize:14.0];
        customLabel.textColor = [UIColor colorWithRed:148/255.0 green:201/255.0 blue:62/255.0 alpha:1.0];
        [customLabel setTextAlignment:UITextAlignmentRight];
        customLabel.backgroundColor = [UIColor clearColor];
        customLabel.adjustsFontSizeToFitWidth = YES;
        
        [self.contentView addSubview:customLabel];
        
        customDetailLabel = [[UILabel alloc] initWithFrame:CGRectMake(115, 7, 185, 30)];
        customDetailLabel.font = [UIFont systemFontOfSize:16.0];
        customDetailLabel.textColor = [UIColor blackColor];
        customDetailLabel.backgroundColor = [UIColor clearColor];
        customDetailLabel.adjustsFontSizeToFitWidth = YES;
        
        [self.contentView addSubview:customDetailLabel];
        
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
