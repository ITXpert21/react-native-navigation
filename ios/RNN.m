
#import "RNN.h"
#import "RNNEventEmitter.h"

#import <React/RCTBridge.h>
#import "RNNSplashScreen.h"

@interface RNN()

@property (readwrite) RCTBridge* bridge;
@property (readwrite) BOOL isReadyToReceiveCommands;
@property (readwrite) RNNEventEmitter* eventEmitter;

@property (readwrite) RNNStore *store;

@end

@implementation RNN


# pragma mark public


+(instancetype)instance
{
	static RNN *sharedInstance = nil;
	static dispatch_once_t onceToken = 0;
	dispatch_once(&onceToken,^{
		if (sharedInstance == nil)
		{
			sharedInstance = [[RNN alloc] init];
		}
	});
	
	return sharedInstance;
}


-(void)bootstrap:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions
{
	self.eventEmitter = [RNNEventEmitter new];
	
	UIApplication.sharedApplication.delegate.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
	UIApplication.sharedApplication.delegate.window.backgroundColor = [UIColor whiteColor];
	
	[RNNSplashScreen show];
	
	[self registerForJsEvents];
	// this will load the JS bundle
	self.bridge = [[RCTBridge alloc] initWithBundleURL:jsCodeLocation moduleProvider:nil launchOptions:launchOptions];
}


# pragma mark private


-(void)registerForJsEvents
{
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onJavaScriptLoaded) name:RCTJavaScriptDidLoadNotification object:self.bridge];
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onJavaScriptWillLoad) name:RCTJavaScriptWillStartLoadingNotification	object:self.bridge];
}


-(void)onJavaScriptLoaded
{
	self.isReadyToReceiveCommands = true;
	[self.eventEmitter sendOnAppLaunched];
}


-(void)onJavaScriptWillLoad
{
	self.store = [RNNStore new];
	[self resetRootViewControllerOnlyOnJSDevReload];
}

-(void)resetRootViewControllerOnlyOnJSDevReload {
	if(![UIApplication.sharedApplication.delegate.window.rootViewController isKindOfClass:[RNNSplashScreen class]]) {
		UIApplication.sharedApplication.delegate.window.rootViewController = nil;
	}
}


@end
