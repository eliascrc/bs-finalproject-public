import React from "react";
import { Route, Switch } from "react-router-dom";
import Home from "./component/home/Home";
import Login from "./component/login/Login";
import SignUp from "./component/sign-up/SignUp";
import ListProducts from "./component/list-products/ListProducts";
import ProductDetails from "./component/product-details/ProductDetails";
import ShoppingBag from "./component/shopping-bag/ShoppingBag";
import MyProfile from "./component/my-profile/MyProfile";

const Routes = () => (
  <Switch>
    <Route exact path="/" component={Home} />
    <Route exact path="/join" component={SignUp} />
    <Route exact path="/product/:id" component={ProductDetails} />
    <Route exact path="/shopping-bag" component={ShoppingBag} />
    <Route exact path="/my-profile" component={MyProfile} />
    <Route path="/login" component={Login} />
    <Route path="/search" component={ListProducts} />
  </Switch>
);

export default Routes;
