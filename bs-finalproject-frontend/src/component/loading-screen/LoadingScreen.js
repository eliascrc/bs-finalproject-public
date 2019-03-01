import React, { Component } from 'react';

import './LoadingScreen.scss';

class LoadingScreen extends Component {

  render() {
    return (
      <div className="loading__wrapper">
        <i className="loading__icon fas fa-carrot"></i>
        <div className="loading__text">
          Loading...
        </div>
      </div>
    )
  }
}

export default LoadingScreen;
