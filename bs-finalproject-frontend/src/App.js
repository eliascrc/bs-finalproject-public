import React, { Component } from 'react';
import './App.scss';
import Header from './component/header/Header';
import Routes from './Routes';
import Footer from './component/footer/Footer';

class App extends Component {
  render() {
    return (
      <div className="App">
        <Header />
        <main className="App__main">
          <Routes />
        </main>
        <Footer />
      </div>
    );
  }
}

export default App;
