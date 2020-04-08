# SIR Model
This is a SIR Model I created in Uni

## Running
To run the model you'll need a JDK (a popup might appear when you try and run it telling you where you can get one).

To run, run the following commands in the top directory:

```
cd bin
java mainSIRS 500 2 0.3 0.4 0.2 visualise
```

Where:
- plot_size = `200` pixels by `200` pixels
- time_step = 1
- p_1 (probability *S*uceptible site becomes *I*nfected) = `0.1`
- p_2 (probability *I*nfected site becomes *R*ecovered) = `0.4`
- p_3 (probability *R*ecovered site becomes *S*usceptible) = `0.2`
- choice to `visualise` the model in a window or `plot` the data into a .dat file = `visualise`
