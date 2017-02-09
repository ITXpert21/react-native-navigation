#import "RNNBridgeModule.h"

#import "RNN.h"
#import "RNNControllerFactory.h"
#import "RNNReactRootViewCreator.h"

@implementation RNNBridgeModule

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue
{
	return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(setRoot:(NSDictionary*)layout)
{
	[self assertReady];
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new]];
	UIViewController *vc = [factory createLayout:layout];
//	if([vc isKindOfClass:[RNNSideMenuController class]]) {
//		vc = ((RNNSideMenuController*)vc).sideMenu;
//	}
	UIApplication.sharedApplication.delegate.window.rootViewController = vc;
	[UIApplication.sharedApplication.delegate.window makeKeyAndVisible];
}

RCT_EXPORT_METHOD(push:(NSString*)containerId layout:(NSDictionary*)layout)
{
	[self assertReady];
	//TODO implement correctly
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new]];
	UIViewController *newVc = [factory createLayout:layout];
	id vc = [UIApplication.sharedApplication.delegate.window.rootViewController childViewControllers][0];
	[[vc navigationController]pushViewController:newVc animated:true];
}

RCT_EXPORT_METHOD(pop:(NSString*)containerId)
{
	[self assertReady];
	//TODO implement correctly
	id vc = [UIApplication.sharedApplication.delegate.window.rootViewController childViewControllers][0];
	[[vc navigationController] popViewControllerAnimated:true];
}

- (void)assertReady
{
	if (![RNN instance].isReadyToReceiveCommands) {
		@throw [NSException exceptionWithName:@"BridgeNotLoadedError" reason:@"Bridge not yet loaded! Send commands after Navigation.events().onAppLaunched() has been called." userInfo:nil];
	}
}

@end

