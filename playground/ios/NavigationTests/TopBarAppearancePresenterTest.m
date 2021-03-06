#import <XCTest/XCTest.h>
#import <OCMock/OCMock.h>
#import <ReactNativeNavigation/TopBarAppearancePresenter.h>
#import "UIViewController+RNNOptions.h"
#import <ReactNativeNavigation/RNNStackController.h>

@interface TopBarAppearancePresenterTest : XCTestCase

@end

@implementation TopBarAppearancePresenterTest {
	TopBarAppearancePresenter* _uut;
	RNNStackController* _stack;
}

- (void)setUp {
    [super setUp];
	_stack = [[RNNStackController alloc] initWithLayoutInfo:nil creator:nil options:[[RNNNavigationOptions alloc] initEmptyOptions] defaultOptions:[[RNNNavigationOptions alloc] initEmptyOptions] presenter:_uut eventEmitter:nil childViewControllers:@[]];
	_uut = [[TopBarAppearancePresenter alloc] initWithNavigationController:_stack];
}

- (void)testMergeOptions_shouldMergeWithDefault {
	RNNNavigationOptions* mergeOptions = [[RNNNavigationOptions alloc] initEmptyOptions];
	RNNNavigationOptions* defaultOptions = [[RNNNavigationOptions alloc] initEmptyOptions];
	defaultOptions.topBar.title.color = [Color withColor:UIColor.redColor];
	
	mergeOptions.topBar.title.fontSize = [Number withValue:@(21)];
	RNNNavigationOptions* withDefault = [mergeOptions withDefault:defaultOptions];
	[_uut mergeOptions:mergeOptions.topBar withDefault:withDefault.topBar];
	XCTAssertEqual(_stack.navigationBar.standardAppearance.titleTextAttributes[NSForegroundColorAttributeName], UIColor.redColor);
	UIFont* font = _stack.navigationBar.standardAppearance.titleTextAttributes[NSFontAttributeName];
	XCTAssertEqual(font.pointSize, 21);
}


@end
