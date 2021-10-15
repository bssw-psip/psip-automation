import { NavItem } from './nav-item';

export let menu: NavItem[] = [
  {
    displayName: 'Home',
    iconName: 'home',
    route: 'home'
  },
  {
    displayName: 'Assessment',
    iconName: 'assignment',
    route: 'user',
    children: [
      {
        displayName: 'Account Info',
        iconName: 'account_box',
        route: 'user/account-info'
      }
    ]
  }
];