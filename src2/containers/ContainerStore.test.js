describe('ContainerStore', () => {
  let uut;

  beforeEach(() => {
    uut = require('./ContainerStore');
  });

  class Component {
    //
  }
  class MyComponent extends Component {
    //
  }

  it('holds containers classes by containerKey', () => {
    uut.setContainerClass('example.mykey', MyComponent);
    expect(uut.getContainerClass('example.mykey')).toEqual(MyComponent);
  });
});
