import React, { useState, useEffect, Component } from "react";
import {Link} from "react-router-dom";
import Zero from "../HomeComponents/Zero";
import InfiniteScroll from 'react-infinite-scroll-component';

class Home extends React.Component {

  state = {
    items: Array.from({ length: 200 })
  };

  fetchMoreData = () => {
    // a fake async api call like which sends
    // 20 more records in 1.5 secs
    setTimeout(() => {
      this.setState({
        items: this.state.items.concat(Array.from({ length: 200 }))
      });
    }, 1500);
  };

  render() 
  {
    return (
      <div>
        <InfiniteScroll
          dataLength={this.state.items.length}
          next={this.fetchMoreData}
          hasMore={true}
          loader={<h4>Loading...</h4>}
        >
          {this.state.items.map((i, index) => (
            <div key={index}>
              div - #{index}
            </div>
          ))}
        </InfiniteScroll>
      </div>
    );
  }

}

export default Home;





