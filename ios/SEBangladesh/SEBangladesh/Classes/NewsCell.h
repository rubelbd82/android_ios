//
//  NewsCell.h
//  SEBangladesh
//
//  Created by Dream on 9/15/13.
//  Copyright (c) 2013 WebInDream.com. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface NewsCell : UITableViewCell {
    UILabel *companyName;
    
    UILabel *newsDetails;
    
}

@property (nonatomic, retain) UILabel *companyName;

@property (nonatomic, retain) UILabel *newsDetails;



@end
