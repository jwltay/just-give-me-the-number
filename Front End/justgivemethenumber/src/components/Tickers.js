import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import { Button, Container, GlobalStyles, Link, Paper } from '@mui/material';

export default function Tickers() {
  const paperStyle = { padding: '50px 20px', width: 800, margin: '20px auto' }

  const [ticker, setTicker] = React.useState('')
  const [tickersAndPrices, setTickersAndPrices] = React.useState([])
  const [editTickerMap, setEditTickerMap] = React.useState({})
  const [apiError, setApiError] = React.useState(null);

  const fetchTickers = () => {
    fetch("http://localhost:8080/")
      .then((res) => {
        if (!res.ok) {
          return res.json().then(result => { throw new Error(result.details)})
        }
        return res.json();
      })
      .then((result) => {
        setTickersAndPrices(result.data);
        console.log(tickersAndPrices);
      })
      .catch((error) => {
        window.showError = true
        setApiError(error.message);
      });
  };

  const handleClickAdd = (e) => {
    e.preventDefault()

    fetch("http://localhost:8080/", {
      method:"POST",
      headers: {"Content-Type":"plain/text"},
      body:ticker
    })
    .then(()=> {
      fetchTickers()
    }, [])
  }

  const handleEditTickerChange = (e, tickerAndPrice) => {
    const updatedEditTickerMap = {
      ...editTickerMap,
      [tickerAndPrice.ticker]: e.target.value
    };
    setEditTickerMap(updatedEditTickerMap);
  };

  const handleClickEdit = (e, tickerAndPrice) => {
    e.preventDefault()

    fetch(`http://localhost:8080/${tickerAndPrice.ticker}`, {
      method:"PATCH",
      headers: {"Content-Type":"plain/text"},
      body:editTickerMap[tickerAndPrice.ticker]
    })
    .then(()=> {
      fetchTickers()
    }, [])
  }

  const handleClickDelete = (e, tickerName) => {
    e.preventDefault()

    fetch(`http://localhost:8080/${tickerName}`, {
      method:"DELETE",
      headers: {"Content-Type":"plain/text"},
      body:tickerName
    })
    .then(()=> {
      fetchTickers()
    }, [])
    .then(() => {
      if (window.showError) {
      window.showError = false
      window.location.reload()
      }
    })
  }

  React.useEffect(()=> {
    fetchTickers()
  }, [])

  return (
      <Container>
        <GlobalStyles styles={{body: { backgroundColor: "#e7ebf0" }}}/>
        <Paper elevation={1} style={paperStyle}>
          <Box
            component="form"
            sx={{
              '& > :not(style)': { m: 1, width: '25ch', height: '55px' },
            }}
            noValidate
            autoComplete="off"
          >
            <TextField
              id="add-ticker"
              label="Add a stock to track"
              variant="outlined"
              value={ticker}
              onChange={(e)=>setTicker(e.target.value)}
            />
            <Button size="small" color="success" variant="contained" onClick={handleClickAdd}>Add</Button>
          </Box>
        </Paper>
        <Paper elevation={1} style={paperStyle}>
          <h4 style={{fontSize:"18px"}}>Tracked stocks</h4>
          {apiError ?
            <div>
              <p>{apiError}</p>
              {apiError.split(' ').length === 6 ? null :
                <Button
                  size="medium"
                  color="primary"
                  variant="contained"
                  onClick={e => {handleClickDelete(e, apiError.split(' ')[3].slice(0, -1))}}
                >Delete {apiError.split(' ')[3].slice(0, -1)}</Button>
              }
            </div>
          : null}
          {tickersAndPrices ?
            <div style={{display:"flex", margin:"8px", justifyContent:"space-evenly", flexWrap:"wrap"}}>
              {tickersAndPrices.map(tickerAndPrice => (
                <Box sx={{ border:1, borderRadius:"8px", borderColor:"grey.500", margin:"8px", padding:"12px", textAlign:"center", color:"text.secondary" }} key={tickerAndPrice.price}>
                  <div style={{display:"flex", flexDirection:"column"}}>
                    <Link href={"http://localhost:8080/" + tickerAndPrice.ticker} color="primary" sx={{fontWeight:"bold"}}>
                      {tickerAndPrice.ticker}
                    </Link>
                    <IconButton
                      aria-label="delete"
                      color="error"
                      onClick={e => {handleClickDelete(e, tickerAndPrice.ticker)}}
                      sx={{width:"38px", top:"-28px", right:'-156px'}}
                    >
                        <DeleteIcon fontSize='small'/>
                    </IconButton>
                  </div>
                  {tickerAndPrice.price}
                  <br/>
                  <br/>
                  <TextField
                    sx={{width:"120px"}}
                    InputProps={{sx:{height:38}}}
                    id="edit-ticker"
                    label="New ticker"
                    variant="outlined"
                    size="small"
                    value={editTickerMap[tickerAndPrice.ticker] || ''}
                    onChange={(e) => handleEditTickerChange(e, tickerAndPrice)}
                  />
                  <Button
                    size="medium"
                    color="primary"
                    variant="contained"
                    onClick={e => {handleClickEdit(e, tickerAndPrice)}}
                  >
                    Edit
                  </Button>
                </Box>
              ))}
            </div>
          : null}
        </Paper>
      </Container>
  );
}
