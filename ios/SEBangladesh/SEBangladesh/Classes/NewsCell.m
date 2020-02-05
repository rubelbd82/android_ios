//
//  NewsCell.m
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "NewsCell.h"

@implementation NewsCell
@synthesize companyName, newsDetails;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
        companyName = [[UILabel alloc] initWithFrame:CGRectMake(5, 7, 280, 20)];
        companyName.font = [UIFont boldSystemFontOfSize:14.0];
        companyName.textColor = [UIColor blackColor];
        companyName.backgroundColor = [UIColor clearColor];
        companyName.adjustsFontSizeToFitWidth = YES;
        [companyName setTextAlignment:UITextAlignmentLeft];
        
        [self.contentView addSubview:companyName];

        
        newsDetails = [[UILabel alloc] initWithFrame:CGRectMake(10, 20, 280, 140)];
        newsDetails.font = [UIFont boldSystemFontOfSize:12.0];
        newsDetails.textColor = [UIColor blackColor];
        [newsDetails setTextAlignment:UITextAlignmentLeft];
        
        newsDetails.backgroundColor = [UIColor clearColor];
        newsDetails.adjustsFontSizeToFitWidth = YES;
        
        newsDetails.numberOfLines = 0;
        newsDetails.lineBreakMode = UILineBreakModeWordWrap;
        newsDetails.lineBreakMode = NSLineBreakByWordWrapping;
        
        
        
        [self.contentView addSubview:newsDetails];

    }
    return self;
}




- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
