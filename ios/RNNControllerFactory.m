
#import "RNNControllerFactory.h"
#import "RNNLayoutNode.h"
#import "RNNRootViewController.h"

@interface RNNControllerFactory ()

@property (nonatomic, strong) id<RNNRootViewCreator> creator;

@end

@implementation RNNControllerFactory

# pragma mark public


-(instancetype)initWithRootViewCreator:(id <RNNRootViewCreator>)creator {
	
	self = [super init];
	self.creator = creator;

	return self;
}

-(UIViewController *)createLayout:(NSDictionary *)layout
{
	return [self fromTree:layout];
}

# pragma mark private

-(UIViewController*)fromTree:(NSDictionary*)json
{
	RNNLayoutNode* node = [RNNLayoutNode create:json];
	
	if (node.isContainer)
	{
		return [self createContainer:node];
	} else if (node.isContainerStack)
	{
		return [self createContainerStack:node];
	} else if (node.isTabs)
	{
		return [self createTabs:node];
	}
	
	@throw [NSException exceptionWithName:@"UnknownControllerType" reason:[@"Unknown controller type " stringByAppendingString:node.type] userInfo:nil];
}

-(UIViewController*)createContainer:(RNNLayoutNode*)node
{
	return [[RNNRootViewController alloc] initWithNode:node rootViewCreator:self.creator];
}

-(UINavigationController*)createContainerStack:(RNNLayoutNode*)node
{
	UINavigationController* vc = [[UINavigationController alloc] init];
	
	NSMutableArray* controllers = [NSMutableArray new];
	for (NSDictionary* child in node.children) {
		[controllers addObject:[self fromTree:child]];
	}
	[vc setViewControllers:controllers];
	
	return vc;
}

-(UITabBarController*)createTabs:(RNNLayoutNode*)node
{
	UITabBarController* vc = [[UITabBarController alloc] init];
	
	NSMutableArray* controllers = [NSMutableArray new];
	for (NSDictionary* child in node.children) {
		UIViewController* childVc = [self fromTree:child];
		
		UITabBarItem* item = [[UITabBarItem alloc] initWithTitle:@"A Tab" image:nil tag:1];
		[childVc setTabBarItem:item];
		[controllers addObject:childVc];
	}
	[vc setViewControllers:controllers];
	
	return vc;
}

@end
