//
//  CustomCell.h
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CustomCell : UITableViewCell {
    UILabel *customLabel;
    UILabel *customDetailLabel;
}

@property (nonatomic, retain) UILabel *customLabel;
@property (nonatomic, retain) UILabel *customDetailLabel;

@end
