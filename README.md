# meta-llama
Yocto layer integrating [llama.cpp](https://github.com/ggml-org/llama.cpp) for the **Phyboard Pollux i.MX 8M Plus** (NXP i.MX8MP).                                                                                        

This layer adds the `llama-cpp` recipe to your BSP image so you can run local LLM inference on the target board.                                                                                                                 

## Usage            
   
1. Clone this layer alongside your other Yocto sources:

```bash                   
   git clone https://forgejo.phytec.de/akapoor/meta-llama.git                                                    
```                                                                                                                       

2. Add the following to build/conf/bblayers.conf: 
 
```bitbake 
   # Add the BSPDIR_Sources variable if not already defined (pointing to your BSP directory)
   # For Phytec BSPs, this is often set to something like: ${OEROOT}/../layer_name 
   
   BBLAYERS += "${BSPDIR_Sources}/meta-llama"
```                                                                                                                        
3. Add the package to your image in build/conf/local.conf:

```bitbake                                                                                       
   IMAGE_INSTALL:append = " llama-cpp"                                                          
```                                                                                                                        
## Notes
Since I had the IMX8MP board on hand, this layer is currently tailored for that platform. However, the `llama-cpp` recipe should be portable to other architectures with minimal adjustments. 

Also since the board variant has 4GB of RAM, the gguf files for [Qwen3.5-0.8B-Q4_K_M.gguf](https://huggingface.co/unsloth/Qwen3.5-0.8B-GGUF) and [Qwen3.5-2B-Q4_K_M.gguf](https://huggingface.co/unsloth/Qwen3.5-2B-GGUF) are tested on the board with llama.cpp.

With the BSP image built and flashed to the board, you can run inference using the `llama-cpp` binary. For example:

```bash
llama-cli -m Qwen3.5-0.8B-Q4_K_M.gguf -t 4 --mmap -cnv --reasoning off
```

The maximum token speed is around 4.1 tokens/s for Qwen3.5-0.8B-Q4_K_M.gguf (with reasoning turned off) with all 4 CPU cores utilized, and CPU temperature is around 90 degrees Celsius.

Further experiments with different models and quantization levels, and contributions to expand support for more models and platforms, are welcome! 