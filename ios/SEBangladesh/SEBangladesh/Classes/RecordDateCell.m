//
//  RecordDateCell.m
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import "RecordDateCell.h"

@implementation RecordDateCell
@synthesize companyName, agmLabel, agmText, recordDateLabel, recordDateText, dividendLabel, dividendText;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
        companyName = [[UILabel alloc] initWithFrame:CGRectMake(5, 7, 280, 20)];
        companyName.font = [UIFont boldSystemFontOfSize:14.0];
        companyName.textColor = [UIColor blackColor];
        [companyName setTextAlignment:UITextAlignmentLeft];
        companyName.backgroundColor = [UIColor clearColor];
        companyName.adjustsFontSizeToFitWidth = YES;
        
        [self.contentView addSubview:companyName];
        
        
        // Dividend Label:
        dividendLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, 30, 75, 20)];
        dividendLabel.font = [UIFont boldSystemFontOfSize:12.0];
        dividendLabel.textColor = [UIColor blackColor];
        [dividendLabel setTextAlignment:UITextAlignmentLeft];
        dividendLabel.backgroundColor = [UIColor clearColor];
        dividendLabel.adjustsFontSizeToFitWidth = YES;
        
        [self.contentView addSubview:dividendLabel];
        
        // Dividend Text:
        dividendText = [[UILabel alloc] initWithFrame:CGRectMake(70, 30, 100, 20)];
        dividendText.font = [UIFont systemFontOfSize:12.0];
        dividendText.textColor = [UIColor blackColor];
        [dividendText setTextAlignment:UITextAlignmentLeft];
        dividendText.backgroundColor = [UIColor clearColor];
        dividendText.adjustsFontSizeToFitWidth = YES;
        
        [self.contentView addSubview:dividendText];


        
        
        // AGM Label:
        agmLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, 50, 100, 20)];
        agmLabel.font = [UIFont boldSystemFontOfSize:12.0];
        agmLabel.textColor = [UIColor blackColor];
        [agmLabel setTextAlignment:UITextAlignmentLeft];
        agmLabel.backgroundColor = [UIColor clearColor];
        agmLabel.adjustsFontSizeToFitWidth = YES;
        
        [self.contentView addSubview:agmLabel];
        
        
        
        // AGM Text:
        agmText = [[UILabel alloc] initWithFrame:CGRectMake(105, 50, 100, 20)];
        agmText.font = [UIFont systemFontOfSize:12.0];
        agmText.textColor = [UIColor blackColor];
        [agmText setTextAlignment:UITextAlignmentLeft];
        agmText.backgroundColor = [UIColor clearColor];
        agmText.adjustsFontSizeToFitWidth = YES;
        
        [self.contentView addSubview:agmText];

        
        
        // Record Date Label:
        recordDateLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, 70, 90, 20)];
        recordDateLabel.font = [UIFont boldSystemFontOfSize:12.0];
        recordDateLabel.textColor = [UIColor blackColor];
        recordDateLabel.backgroundColor = [UIColor clearColor];
        recordDateLabel.adjustsFontSizeToFitWidth = YES;
        [recordDateLabel setTextAlignment:UITextAlignmentLeft];
        
        [self.contentView addSubview:recordDateLabel];

        
        
        
        // Record Date Text:
        recordDateText = [[UILabel alloc] initWithFrame:CGRectMake(87, 70, 90, 20)];
        recordDateText.font = [UIFont systemFontOfSize:12.0];
        recordDateText.textColor = [UIColor blackColor];
        recordDateText.backgroundColor = [UIColor clearColor];
        recordDateText.adjustsFontSizeToFitWidth = YES;
        [recordDateText setTextAlignment:UITextAlignmentLeft];
        
        [self.contentView addSubview:recordDateText];

        
    }

    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
