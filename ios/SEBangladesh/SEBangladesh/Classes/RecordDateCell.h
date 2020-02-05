//
//  RecordDateCell.h
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RecordDateCell : UITableViewCell {
    UILabel *companyName;
    
    UILabel *dividendLabel;
    UILabel *dividendText;
    
    UILabel *agmLabel;
    UILabel *agmText;
    
    UILabel *recordDateLabel;
    UILabel *recordDateText;
}

@property (nonatomic, retain) UILabel *companyName;

@property (nonatomic, retain) UILabel *dividendLabel;
@property (nonatomic, retain) UILabel *dividendText;

@property (nonatomic, retain) UILabel *agmLabel;
@property (nonatomic, retain) UILabel *agmText;

@property (nonatomic, retain) UILabel *recordDateLabel;
@property (nonatomic, retain) UILabel *recordDateText;


@end
